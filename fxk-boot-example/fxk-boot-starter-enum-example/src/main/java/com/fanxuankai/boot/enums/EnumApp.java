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
        model.setAuth("fanxuankai");
        model.setPath("/Users/fanxuankai/Java/Workspace/myproject/fanxuankai/framework/标准/fxk-boot/fxk-boot/example/" +
                "fxk-boot-starter-enum-sample/src/main/java");
        model.setPackageName("com.fanxuankai.boot.enums");
        model.setGenerateDataOnly(false);
        model.setIncrement(false);
        enumGenerator.generate(model);
        System.exit(0);
    }
}
