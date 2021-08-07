package com.fanxuankai.boot.fluent.mybatis.dao;

import cn.org.atool.fluent.mybatis.metadata.JoinType;
import cn.org.atool.fluent.mybatis.segment.JoinQuery;
import com.fanxuankai.boot.fluent.mybatis.dao.base.HelloWorldBaseDao;
import com.fanxuankai.boot.fluent.mybatis.wrapper.HelloWorldQuery;
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
public class HelloWorldDaoTest {
    @Resource
    private HelloWorldBaseDao dao;

    @Test
    public void delete() {
        dao.deleteById(1);
    }

    @Test
    public void query() {
        HelloWorldQuery leftQuery = HelloWorldQuery.defaultQuery().where.sayHello().eq(2).end();
        HelloWorldQuery rightQuery = HelloWorldQuery.defaultQuery().where.sayHello().eq(2).end();
        JoinQuery query = leftQuery.join(JoinType.LeftJoin, rightQuery)
                .on(l -> l.where.id(), r -> r.where.id())
                .endJoin()
                .build();

    }
}
