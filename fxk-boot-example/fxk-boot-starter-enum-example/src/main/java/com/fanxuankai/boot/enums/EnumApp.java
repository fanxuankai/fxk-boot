package com.fanxuankai.boot.enums;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * @author fanxuankai
 */
@SpringBootApplication
public class EnumApp implements ApplicationRunner {
    @Resource
    private EnumGenerator enumGenerator;

    public static void main(String[] args) {
        SpringApplication.run(EnumApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        // 生成枚举类
        GenerateModel model = new GenerateModel();
        model.setAuth("admin");
        model.setPath("/Users/fanxuankai/Java/Workspace/myproject/fanxuankai/framework/标准/fxk-boot/fxk-boot-example/" +
                "fxk-boot-starter-enum-example/src/main/java");
        model.setPackageName("com.fanxuankai.boot.enums.files");
        model.setGenerateDataOnly(false);
        enumGenerator.generate(model);
    }
}
