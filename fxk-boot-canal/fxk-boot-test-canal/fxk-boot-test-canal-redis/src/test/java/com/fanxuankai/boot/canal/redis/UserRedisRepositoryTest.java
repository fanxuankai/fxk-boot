package com.fanxuankai.boot.canal.redis;

import com.alibaba.fastjson.JSON;
import com.fanxuankai.boot.canal.redis.domain.User;
import com.fanxuankai.boot.canal.redis.model.CombineKeyModel;
import com.fanxuankai.boot.canal.redis.model.Entry;
import com.fanxuankai.boot.canal.redis.model.UniqueKey;
import com.fanxuankai.boot.canal.redis.model.UniqueKeyPro;
import com.fanxuankai.boot.canal.redis.repository.UserRedisRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author fanxuankai
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRedisRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRedisRepositoryTest.class);

    @Resource
    private UserRedisRepository userRedisRepository;

    @Test
    public void findById() {
        userRedisRepository.findAll();
        userRedisRepository.findById(1L).ifPresent(System.out::print);
    }

    @Test
    public void findAllById() {
        log(userRedisRepository.findAllById(Arrays.asList(472546446994836472L, 472546446994836473L)));
    }

    @Test
    public void test() {
        LOGGER.info(JSON.toJSONString(userRedisRepository.findAll(), true));
        LOGGER.info(JSON.toJSONString(userRedisRepository.findOne(new UniqueKey("phone", "458361630032396291"))));
    }

    @Test
    public void findAll() {
        List<User> all = userRedisRepository.findAll();
        LOGGER.info("{}", JSON.toJSONString(all, true));
    }

    @Test
    public void testAll() {
        CombineKeyModel ck = new CombineKeyModel(Arrays.asList(new Entry("username", "jCplH5C")
                , new Entry("password", "rYR")));
        UniqueKey uk = new UniqueKey("phone", "F4Nh");
        UniqueKeyPro ukPro = new UniqueKeyPro("phone", Arrays.asList("QdWU", "cUsgBbeGN"));

        LOGGER.info("{}", userRedisRepository.count());
        LOGGER.info("{}", userRedisRepository.exists(uk));
        log(userRedisRepository.existsById(458353649886691328L));
        log(userRedisRepository.findById(458353649886691328L));
        userRedisRepository.findOne(uk).ifPresent(this::log);
        userRedisRepository.findOne(ck).ifPresent(this::log);
        log(userRedisRepository.findAll(ukPro));
        log(userRedisRepository.findAllById(Arrays.asList(458353649886691328L, 458353649895079936L)));
        userRedisRepository.findAll(Arrays.asList("username", "password")).forEach(this::log);
        userRedisRepository.findAll().forEach(this::log);
    }

    private void log(Object o) {
        LOGGER.info("{}", JSON.toJSONString(o, true));
    }
}
