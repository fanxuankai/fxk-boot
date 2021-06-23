package com.fanxuankai.boot.generator;

import cn.hutool.core.text.StrPool;
import com.fanxuankai.boot.generator.autoconfigure.CodeGeneratorProperties;
import com.fanxuankai.boot.generator.service.GeneratorService;
import com.fanxuankai.commons.util.OptionalUtils;
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
        OptionalUtils.of(generatorProperties.getTables())
                .ifPresent(tables -> {
                    for (String file : tables.split(StrPool.COMMA)) {
                        generatorService.generateCode(file);
                    }
                });
    }
}
