package com.fanxuankai.boot.enums;

import com.alibaba.fastjson.JSON;
import com.fanxuankai.boot.enums.domain.Enum;
import com.fanxuankai.boot.enums.domain.EnumType;
import com.fanxuankai.boot.enums.service.EnumService;
import com.fanxuankai.boot.enums.service.EnumTypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
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
@RunWith(SpringRunner.class)
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
        list.add(new EnumDTO.Enum().setName("white").setValue("白色"));
        list.add(new EnumDTO.Enum().setName("red").setValue("红色"));
        list.add(new EnumDTO.Enum().setName("black").setValue("黑色"));
        enumService.add(new EnumDTO().setEnumType(new EnumDTO.EnumType().setName("colour").setDescription("颜色")).setEnumList(list));
        list = new ArrayList<>(2);
        list.add(new EnumDTO.Enum().setName("no").setValue("未删除"));
        list.add(new EnumDTO.Enum().setName("yes").setValue("已删除"));
        enumService.add(new EnumDTO().setEnumType(new EnumDTO.EnumType().setName("deleted").setDescription("是否删除")).setEnumList(list));
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
        enumGenerator.generate(new GenerateModel()
                .setAuth("fanxuankai")
                .setPath("/Users/fanxuankai/Java/Workspace/myproject/fanxuankai/framework/boot/enum-manager-spring" +
                        "-boot/enum-mysql-spring-boot-test/src/test/java")
                .setPackageName("com.fanxuankai.boot.enums"));
    }
}
