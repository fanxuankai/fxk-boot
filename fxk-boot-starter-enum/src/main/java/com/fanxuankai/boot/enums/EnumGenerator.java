package com.fanxuankai.boot.enums;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.fanxuankai.boot.enums.domain.Enum;
import com.fanxuankai.boot.enums.domain.EnumType;
import com.fanxuankai.boot.enums.service.EnumService;
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
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
@Service
public class EnumGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnumGenerator.class);
    @Resource
    private EnumService enumService;

    /**
     * 生成枚举数据和枚举类
     *
     * @param generateModel /
     */
    @Transactional(rollbackFor = Exception.class)
    public void generate(GenerateModel generateModel) {
        List<EnumDTO> dtoList = JSONUtil.toList(getEnumJsonString(), EnumDTO.class);
        check(dtoList);
        enumService.setupCode(dtoList);
        dtoList = filter(dtoList);
        if (dtoList.isEmpty()) {
            return;
        }
        // 先删除如果存在的数据
        List<String> typeNames = dtoList.stream().map(o -> o.getEnumType().getName()).collect(Collectors.toList());
        enumService.delete(typeNames);
        // 插入枚举数据
        enumService.add(dtoList);
        // 取出保存的枚举数据,用于生成代码
        List<EnumVO> list;
        if (generateModel.isGenerateDataOnly() || (list = enumService.list(typeNames, false)).isEmpty()) {
            return;
        }
        List<EnumVO> enumList = list.stream().map(vo -> {
            List<Enum> enumDomainList = vo.getEnumList();
            enumDomainList.sort(Comparator.comparing(Enum::getCode));
            enumDomainList.forEach(anEnum ->
                    anEnum.setName(StrUtil.toUnderlineCase(anEnum.getName()).toUpperCase()));
            EnumType anEnumType = new EnumType();
            anEnumType.setName(StringUtils.capitalize(vo.getEnumType().getName()));
            anEnumType.setDescription(vo.getEnumType().getDescription());
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
            throw new RuntimeException(e);
        }
    }

    /**
     * 过滤发生变化的枚举数据
     *
     * @param dtoList 枚举列表
     * @return /
     */
    private List<EnumDTO> filter(List<EnumDTO> dtoList) {
        if (dtoList.isEmpty()) {
            return Collections.emptyList();
        }
        // 数据库的枚举数据
        // key: 枚举类名 value: EnumVO
        Map<String, EnumVO> map = enumService.all()
                .stream()
                .collect(Collectors.toMap(o -> o.getEnumType().getName(), Function.identity()));
        return dtoList.stream().filter(o -> {
            EnumVO enumVO = map.get(o.getEnumType().getName());
            if (enumVO == null) {
                // 数据库不存在,则为新枚举
                return true;
            }
            return !Objects.equals(enumVO.getEnumType(), o.getEnumType())
                    || !Objects.equals(enumVO.getEnumList(), o.getEnumList());
        }).collect(Collectors.toList());

    }

    /**
     * 枚举数据合法校验
     *
     * @param dtoList 枚举列表
     */
    private void check(List<EnumDTO> dtoList) {
        dtoList.stream().collect(Collectors.groupingBy(o -> o.getEnumType().getName()))
                .forEach((s, enums) -> {
                    if (enums.size() > 1) {
                        throw new IllegalArgumentException("枚举类型名重复: " + s);
                    }
                    EnumDTO enumDTO = enums.get(0);
                    Set<Integer> codes = new HashSet<>();
                    Set<String> names = new HashSet<>();
                    for (Enum anEnum : enumDTO.getEnumList()) {
                        if (anEnum.getCode() != null && codes.contains(anEnum.getCode())) {
                            throw new IllegalArgumentException("枚举类型: " + s + ", code 重复: " + anEnum.getCode());
                        } else {
                            codes.add(anEnum.getCode());
                        }
                        if (anEnum.getName() != null && names.contains(anEnum.getName())) {
                            throw new IllegalArgumentException("枚举类型: " + s + ", name 重复: " + anEnum.getName());
                        } else {
                            names.add(anEnum.getName());
                        }
                    }
                    // 清空不能手动赋值的字段
                    enums.forEach(o -> {
                        o.getEnumType().setId(null);
                        o.getEnumList().forEach(anEnum -> {
                            anEnum.setId(null);
                            anEnum.setTypeId(null);
                        });
                    });
                });
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
}
