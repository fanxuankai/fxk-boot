package com.fanxuankai.boot.enums;

import com.alibaba.fastjson.JSON;
import com.fanxuankai.boot.enums.domain.Enum;
import com.fanxuankai.boot.enums.domain.EnumType;
import com.fanxuankai.boot.enums.service.EnumService;
import com.fanxuankai.boot.enums.service.EnumTypeService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
@SpringBootTest
public class EnumTest {
    @Resource
    private EnumService enumService;
    @Resource
    private EnumTypeService enumTypeService;
    @Resource
    private EnumGenerator enumGenerator;

    @Test
    public void testAll() {
        delete();
        add();
        generate();
    }

    @Test
    public void delete() {
        enumService.removeByIds(enumService.list().stream().map(Enum::getId)
                .collect(Collectors.toList()));
        enumTypeService.removeByIds(enumTypeService.list().stream().map(EnumType::getId)
                .collect(Collectors.toList()));
    }

    @Test
    public void addAll() {
        List<EnumDTO.Enum> list = new ArrayList<>(3);
        EnumDTO.Enum white = new EnumDTO.Enum();
        white.setName("white");
        white.setValue("白色");
        list.add(white);
        EnumDTO.Enum red = new EnumDTO.Enum();
        red.setName("red");
        red.setValue("红色");
        list.add(red);
        EnumDTO.Enum black = new EnumDTO.Enum();
        black.setName("black");
        black.setValue("黑色");
        list.add(black);
        EnumDTO.EnumType enumType = new EnumDTO.EnumType();
        enumType.setName("colour");
        enumType.setDescription("颜色");
        EnumDTO enumDTO = new EnumDTO();
        enumDTO.setEnumType(enumType);
        enumDTO.setEnumList(list);
        enumService.add(enumDTO);
        list = new ArrayList<>(2);
        EnumDTO.Enum no = new EnumDTO.Enum();
        no.setName("no");
        no.setValue("未删除");
        list.add(no);
        EnumDTO.Enum yes = new EnumDTO.Enum();
        yes.setName("yes");
        yes.setValue("已删除");
        list.add(yes);
        EnumDTO.EnumType yesOrNo = new EnumDTO.EnumType();
        yesOrNo.setName("deleted");
        yesOrNo.setDescription("是否删除");
        EnumDTO yesOrNoDto = new EnumDTO();
        yesOrNoDto.setEnumType(yesOrNo);
        yesOrNoDto.setEnumList(list);
        enumService.add(yesOrNoDto);
    }

    private String getEnumJSONString() {
        try {
            return String.join("\n",
                    Files.readAllLines(Paths.get(getClass().getResource("/enum.json").getFile())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void add() {
        Objects.requireNonNull(JSON.parseArray(getEnumJSONString(), EnumDTO.class))
                .forEach(enumDTO -> enumService.add(enumDTO));
    }

    @Test
    public void list() {
        System.out.println(JSON.toJSONString(enumService.find("deleted"), true));
    }

    @Test
    public void generate() {
        GenerateModel generateModel = new GenerateModel();
        generateModel.setAuth("fanxuankai");
        generateModel.setPath(new File("").getAbsoluteFile().getAbsolutePath() + "/src/test/java");
        generateModel.setPackageName("com.fanxuankai.boot.enums");
        enumGenerator.generate(generateModel);
    }
}
