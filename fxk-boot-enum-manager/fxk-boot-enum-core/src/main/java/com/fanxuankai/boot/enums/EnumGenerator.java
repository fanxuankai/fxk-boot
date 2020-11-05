package com.fanxuankai.boot.enums;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fanxuankai.boot.enums.domain.Enum;
import com.fanxuankai.boot.enums.domain.EnumType;
import com.fanxuankai.boot.enums.service.EnumService;
import com.fanxuankai.boot.enums.service.EnumTypeService;
import com.google.common.base.CaseFormat;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
@Service
@Slf4j
public class EnumGenerator {
    @Resource
    private EnumTypeService enumTypeService;
    @Resource
    private EnumService enumService;

    /**
     * 生成枚举数据和枚举类
     *
     * @param generateModel 不需要创建枚举类时传 null
     */
    @Transactional(rollbackFor = Exception.class)
    public void generate(@Nullable GenerateModel generateModel) {
        // 清空枚举数据
        delete();
        // 插入枚举数据
        add();
        if (generateModel == null) {
            return;
        }
        List<EnumType> enumTypes = enumTypeService.list();
        if (enumTypes.isEmpty()) {
            return;
        }
        Map<Long, List<Enum>> map = map(enumTypes.stream().map(EnumType::getId).collect(Collectors.toList()));
        List<EnumVO> enumList = enumTypes.stream()
                .filter(enumType -> map.get(enumType.getId()) != null)
                .sorted(Comparator.comparing(EnumType::getId))
                .map(enumType -> {
                    List<Enum> enumDomainList = map.get(enumType.getId());
                    enumDomainList.sort(Comparator.comparing(Enum::getCode));
                    enumDomainList.forEach(anEnum ->
                            anEnum.setName(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE,
                                    anEnum.getName())));
                    return new EnumVO()
                            .setEnumType(new EnumType().setName(StringUtils.capitalize(enumType.getName()))
                                    .setDescription(enumType.getDescription()))
                            .setEnumList(enumDomainList);
                }).collect(Collectors.toList());
        Configuration cfg = new Configuration(Configuration.getVersion());
        cfg.setClassLoaderForTemplateLoading(getClass().getClassLoader(), "templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        String name = "";
        try {
            Template template = cfg.getTemplate("enum.ftl");
            for (EnumVO enumVO : enumList) {
                EnumModel model = new EnumModel()
                        .setPackageName(generateModel.getPackageName())
                        .setAuth(generateModel.getAuth())
                        .setEnumVO(enumVO);
                String shortName = enumVO.getEnumType().getName();
                String fileName = ClassUtils.convertClassNameToResourcePath(generateModel.getPackageName())
                        + "/" + shortName;
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(
                        generateModel.getPath() + "/" + fileName + ".java"))));
                template.process(model, writer);
                name = generateModel.getPackageName() + "." + shortName;
                log.info("生成枚举 {} 成功", name);
                writer.close();
            }
        } catch (IOException | TemplateException e) {
            log.error(String.format("生成枚举 %s 失败", name), e);
        }
    }

    private void delete() {
        enumService.removeByIds(enumService.list().stream().map(Enum::getId)
                .collect(Collectors.toList()));
        enumTypeService.removeByIds(enumTypeService.list().stream().map(EnumType::getId)
                .collect(Collectors.toList()));
    }

    private void add() {
        Objects.requireNonNull(JSON.parseArray(getEnumJsonString(), EnumDTO.class))
                .forEach(enumDTO -> enumService.add(enumDTO));
    }

    private String getEnumJsonString() {
        try {
            ClassPathResource classPathResource = new ClassPathResource("enum.json");
            InputStream inputStream = classPathResource.getInputStream();
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString(StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            throw new RuntimeException("找不到枚举 JSON 文件", e);
        }
    }

    private Map<Long, List<Enum>> map(List<Long> typeIds) {
        return enumService.list(new QueryWrapper<Enum>().lambda()
                .in(Enum::getTypeId, typeIds))
                .stream()
                .collect(Collectors.groupingBy(Enum::getTypeId));
    }
}
