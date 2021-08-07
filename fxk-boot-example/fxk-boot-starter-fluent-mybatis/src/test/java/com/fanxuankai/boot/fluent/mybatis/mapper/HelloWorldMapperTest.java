package com.fanxuankai.boot.fluent.mybatis.mapper;

import com.fanxuankai.boot.fluent.mybatis.domain.HelloWorldEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author fanxuankai
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class HelloWorldMapperTest {
    @Resource
    private HelloWorldMapper mapper;

    @Test
    public void test() {
        mapper.delete(mapper.query()
                .where.id().eq(1L).end());
        HelloWorldEntity entity = new HelloWorldEntity();
        entity.setId(1L);
        entity.setSayHello("hello world");
        entity.setYourName("fluent mybatis");
        entity.setIsDeleted(false);
        mapper.insert(entity);
        HelloWorldEntity result1 = mapper.findOne(mapper.query()
                .where.id().eq(1L).end());
        System.out.println("1. HelloWorldEntity:" + result1.toString());
        mapper.updateBy(mapper.updater()
                .set.sayHello().is("say hello, say hello!")
                .set.yourName().is("fluent mybatis is powerful!")
                .end()
                .where.id().eq(1L).end()
        );
        HelloWorldEntity result2 = mapper.findOne(mapper.query()
                .where.sayHello().like("hello")
                .and.isDeleted().eq(false).end()
                .limit(1)
        );
        System.out.println("2. HelloWorldEntity:" + result2.toString());
    }
}
