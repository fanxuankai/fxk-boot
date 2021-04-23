package com.fanxuankai.boot.enums;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fanxuankai.boot.enums.domain.Enum;
import com.fanxuankai.boot.enums.domain.EnumType;
import com.fanxuankai.boot.enums.service.EnumService;
import com.fanxuankai.boot.enums.service.EnumTypeService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
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
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
@Service
public class EnumGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnumGenerator.class);
    @Resource
    private EnumTypeService enumTypeService;
    @Resource
    private EnumService enumService;

    /**
     * 生成枚举数据和枚举类
     *
     * @param generateModel /
     */
    @Transactional(rollbackFor = Exception.class)
    public void generate(GenerateModel generateModel) {
        List<EnumType> enumTypes;
        if (generateModel.isIncrement()) {
            List<EnumDTO> incrementEnumDtoList = enumService.addIncrement(getEnumFromJson());
            if (incrementEnumDtoList.isEmpty()) {
                return;
            }
            enumTypes = enumTypeService.list(incrementEnumDtoList.stream()
                    .map(EnumDTO::getEnumType)
                    .map(EnumDTO.EnumType::getName).collect(Collectors.toList()));
        } else {
            // 清空枚举数据
            delete();
            // 插入枚举数据
            enumService.add(getEnumFromJson());
            enumTypes = enumTypeService.list();
        }
        if (generateModel.isGenerateDataOnly() || enumTypes.isEmpty()) {
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
                            anEnum.setName(StrUtil.toUnderlineCase(anEnum.getName()).toUpperCase()));
                    EnumType anEnumType = new EnumType();
                    anEnumType.setName(StringUtils.capitalize(enumType.getName()));
                    anEnumType.setDescription(enumType.getDescription());
                    EnumVO enumVO = new EnumVO();
                    enumVO.setEnumType(anEnumType);
                    enumVO.setEnumList(enumDomainList);
                    return enumVO;
                }).collect(Collectors.toList());
        Configuration cfg = new Configuration(Configuration.getVersion());
        cfg.setClassLoaderForTemplateLoading(getClass().getClassLoader(), "templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        String name = "";
        try {
            Template template = cfg.getTemplate("enum.ftl");
            for (EnumVO enumVO : enumList) {
                EnumModel model = new EnumModel();
                model.setPackageName(generateModel.getPackageName());
                model.setAuth(generateModel.getAuth());
                model.setEnumVO(enumVO);
                String shortName = enumVO.getEnumType().getName();
                String fileName = ClassUtils.convertClassNameToResourcePath(generateModel.getPackageName())
                        + "/" + shortName;
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(
                        generateModel.getPath() + "/" + fileName + ".java"))));
                template.process(model, writer);
                name = generateModel.getPackageName() + "." + shortName;
                LOGGER.info("生成枚举 {} 成功", name);
                writer.close();
            }
        } catch (IOException | TemplateException e) {
            LOGGER.error(String.format("生成枚举 %s 失败", name), e);
        }
    }

    private void delete() {
        enumService.removeByIds(enumService.list().stream().map(Enum::getId)
                .collect(Collectors.toList()));
        enumTypeService.removeByIds(enumTypeService.list().stream().map(EnumType::getId)
                .collect(Collectors.toList()));
    }

    private List<EnumDTO> getEnumFromJson() {
        return JSONUtil.toList(getEnumJsonString(), EnumDTO.class);
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
