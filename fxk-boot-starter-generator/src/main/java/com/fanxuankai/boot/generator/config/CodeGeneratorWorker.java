package com.fanxuankai.boot.generator.config;

import com.fanxuankai.boot.generator.service.GeneratorService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author fanxuankai
 */
@Component
public class CodeGeneratorWorker implements ApplicationRunner {
    @Resource
    private GeneratorService generatorService;
    @Resource
    private CodeGeneratorProperties generatorProperties;

    @Override
    public void run(ApplicationArguments args) {
        generate();
    }

    /**
     * 生成代码
     */
    private void generate() {
        String regex = ",";
        for (String file : generatorProperties.getTables()
                .split(regex)) {
            generatorService.generateCode(file);
        }
        System.exit(0);
    }
}
