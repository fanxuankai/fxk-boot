package com.fanxuankai.boot.fluent.mybatis;

import cn.org.atool.fluent.mybatis.model.StdPagedList;
import com.fanxuankai.boot.fluent.mybatis.domain.StudentScore;
import com.fanxuankai.boot.fluent.mybatis.mapper.StudentScoreMapper;
import com.fanxuankai.boot.fluent.mybatis.wrapper.StudentScoreQuery;
import com.github.jsonzou.jmockdata.JMockData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author fanxuankai
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class StudentScoreTest {
    @Resource
    private StudentScoreMapper mapper;

    @Test
    public void init() {
        mapper.delete(StudentScoreQuery.query());
        mapper.insertBatch(IntStream.range(0, 100)
                .mapToObj(value -> JMockData.mock(StudentScore.class))
                .collect(Collectors.toList()));
    }

    @Test
    public void summary() {
        StudentScoreQuery query = StudentScoreQuery.query()
                .select
                .schoolTerm()
                .subject()
                .count.score("count")
                .min.score("min_score")
                .max.score("max_score")
                .avg.score("avg_score")
                .end()
                .where.schoolTerm().ge(2000)
                .and.subject().in(new String[]{"英语", "数学", "语文"})
                .and.score().ge(60)
                .and.isDeleted().isFalse()
                .end()
                .groupBy.schoolTerm().subject().end()
                .having.count.score().gt(1).end()
                .orderBy.schoolTerm().asc().subject().asc().end();
        List<Map<String, Object>> summary = mapper.listMaps(query);
        System.out.println(summary);
    }

    @Test
    public void page() {
        StdPagedList<StudentScore> page = mapper.stdPagedEntity(StudentScoreQuery.query()
                .where.subject().eq("语文").end().limit(20, 10));
        System.out.println(page);
    }

    @Test
    public void count() {
        System.out.println(mapper.count(StudentScoreQuery.query().where.isDeleted().isFalse().end()));
    }
}
