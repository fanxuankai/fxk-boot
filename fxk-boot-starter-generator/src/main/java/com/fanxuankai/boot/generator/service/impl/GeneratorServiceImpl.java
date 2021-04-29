package com.fanxuankai.boot.generator.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.fanxuankai.boot.generator.autoconfigure.CodeGeneratorProperties;
import com.fanxuankai.boot.generator.constants.Constants;
import com.fanxuankai.boot.generator.model.ColumnInfo;
import com.fanxuankai.boot.generator.model.GenConfig;
import com.fanxuankai.boot.generator.service.GeneratorService;
import com.fanxuankai.boot.generator.strategy.TemplateGenerator;
import com.fanxuankai.boot.generator.strategy.annotation.TemplateFileAnnotation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author fanxuankai
 */
@Service
public class GeneratorServiceImpl implements GeneratorService, ApplicationContextAware {
    private final CodeGeneratorProperties properties;
    private final JdbcTemplate jdbcTemplate;
    private Collection<TemplateGenerator> templateGenerators;
    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneratorServiceImpl.class);

    public GeneratorServiceImpl(CodeGeneratorProperties properties, JdbcTemplate jdbcTemplate) {
        this.properties = properties;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 查询数据库的表字段数据数据
     *
     * @param tableName 表名
     * @return /
     */
    private GenConfig query(String tableName) {
        GenConfig genConfig = new GenConfig();
        genConfig.setTableName(tableName);
        String tableInfoSql = String.format("select table_comment from information_schema.tables where table_schema =" +
                        " '%s' and table_name = '%s'"
                , properties.getSchema(), tableName);
        genConfig.setComment(jdbcTemplate.queryForObject(tableInfoSql, String.class));
        String sql = String.format("select column_name, is_nullable, data_type, column_comment, column_key, extra " +
                        "from information_schema.columns where table_schema = '%s' and table_name = '%s' order by " +
                        "ordinal_position"
                , properties.getSchema(), tableName);
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        List<ColumnInfo> columnInfos = ListUtil.list(false);
        for (Map<String, Object> map : result) {
            String columnComment = Optional.ofNullable(map.get("column_comment")).map(Object::toString).orElse(null);
            String columnKey = Optional.ofNullable(map.get("column_key")).map(Object::toString).orElse(null);
            String extra = Optional.ofNullable(map.get("extra")).map(Object::toString).orElse(null);
            ColumnInfo columnInfo = new ColumnInfo();
            columnInfo.setColumnName(map.get("column_name").toString());
            columnInfo.setNotNull("NO".equals(map.get("is_nullable")));
            columnInfo.setColumnType(map.get("data_type").toString());
            columnInfo.setRemark(columnComment);
            columnInfo.setExtra(extra);
            columnInfo.setQueryType("EQ");
            columnInfo.setPrimaryKey(Objects.equals(Constants.PK, columnKey));
            columnInfo.setUnique(Objects.equals(Constants.UNI, columnKey));
            if (Constants.PK.equalsIgnoreCase(columnKey) && Constants.AUTO_INCREMENT.equalsIgnoreCase(extra)) {
                columnInfo.setNotNull(false);
            }
            Optional.ofNullable(properties.getAutoFill().get(columnInfo.getColumnName()))
                    .ifPresent(columnInfo::setFill);
            if (CollectionUtils.isEmpty(properties.getFormExcludeColumns())) {
                columnInfo.setFormShow(true);
            } else {
                columnInfo.setFormShow(!properties.getFormExcludeColumns().contains(columnInfo.getColumnName()));
            }
            if (CollectionUtils.isEmpty(properties.getListExcludeColumns())) {
                columnInfo.setListShow(true);
            } else {
                columnInfo.setListShow(!properties.getListExcludeColumns().contains(columnInfo.getColumnName()));
            }
            columnInfos.add(columnInfo);
        }
        genConfig.setColumnInfos(columnInfos);
        return genConfig;
    }

    @Override
    public void initConfig(String tableName) {
        GenConfig genConfig = query(tableName);
        BeanUtil.copyProperties(properties, genConfig);
        genConfig.setTemplates(Collections.emptySet());
        File file = getConfigPath(tableName);
        FileUtil.touch(file);
        try {
            mapper.writeValue(file, genConfig);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void generateCode(String tableName) {
        // 如果没有配置则自动初始化配置
        boolean go;
        if (FileUtil.exist(getConfigPath(tableName))) {
            go = true;
        } else {
            initConfig(tableName);
            LOGGER.info("初始化配置成功: {}", tableName);
            Scanner scanner = new Scanner(System.in);
            System.out.println("请 Reload from Disk 后核对配置文件(resources/config), 生成代码[go] or 结束[任意字符]");
            String s = scanner.nextLine();
            go = "go".equalsIgnoreCase(s);
        }
        if (!go) {
            return;
        }
        final GenConfig genConfig = getGenConfig(tableName);
        for (TemplateGenerator templateGenerator : templateGenerators) {
            TemplateFileAnnotation templateFileAnnotation =
                    templateGenerator.getClass().getAnnotation(TemplateFileAnnotation.class);
            if (templateFileAnnotation == null) {
                continue;
            }
            if (!genConfig.getTemplates().isEmpty()
                    && !genConfig.getTemplates().contains(templateFileAnnotation.value().getName())) {
                continue;
            }
            try {
                templateGenerator.generate(genConfig, properties);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        LOGGER.info("生成成功: {}", tableName);
    }

    private GenConfig getGenConfig(String tableName) {
        File file = getConfigPath(tableName);
        GenConfig genConfig;
        try {
            genConfig = mapper.readValue(file, GenConfig.class);
            genConfig.getColumnInfos().forEach(columnInfo -> {
                if (StrUtil.isNotBlank(columnInfo.getQueryType())) {
                    columnInfo.setQueryType(columnInfo.getQueryType().toUpperCase());
                }
                if (StrUtil.isNotBlank(columnInfo.getFill())) {
                    columnInfo.setFill(columnInfo.getFill().toUpperCase());
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return genConfig;
    }

    private File getConfigPath(String tableName) {
        String projectDir = StrUtil.subBefore(ClassUtil.getClassPath(), "/target/classes", true);
        String yml = String.format("/config/%s/%s.yml", properties.getSchema(), tableName);
        return new File(projectDir + "/src/main/resources" + yml);
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        templateGenerators = applicationContext.getBeansOfType(TemplateGenerator.class).values();
    }
}
