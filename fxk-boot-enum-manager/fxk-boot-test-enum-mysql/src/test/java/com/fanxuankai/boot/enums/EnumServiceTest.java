package com.fanxuankai.boot.enums;

import com.fanxuankai.boot.enums.service.EnumService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author fanxuankai
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class EnumServiceTest {

    @Resource
    private EnumService enumService;

    @Test
    public void list() {
        System.out.println(enumService.list(Arrays.asList("deleted", "colour")));
        System.out.println(enumService.find("deleted"));
        System.out.println(enumService.all());
    }
}
