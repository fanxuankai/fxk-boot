package com.fanxuankai.boot.canal.autoconfigure;

import cn.hutool.extra.spring.SpringUtil;
import com.fanxuankai.canal.core.CanalWorker;
import com.fanxuankai.canal.core.config.CanalWorkConfiguration;
import com.fanxuankai.commons.core.util.concurrent.ThreadPool;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.concurrent.ExecutorService;

/**
 * @author fanxuankai
 */
public class CanalAutoConfiguration implements ApplicationRunner {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    @Lazy
    private ExecutorService executorService;

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean
    public ExecutorService executorService() {
        return ThreadPool.INSTANCE.getExecutor();
    }

    @Override
    public void run(ApplicationArguments args) {
        Collection<CanalWorker> canalWorkers =
                SpringUtil.getApplicationContext().getBeansOfType(CanalWorker.class).values();
        canalWorkers.stream()
                .peek(canalWorker -> {
                    CanalWorkConfiguration canalWorkConfiguration = canalWorker.getCanalWorkConfiguration();
                    canalWorkConfiguration.setRedisTemplate(redisTemplate);
                    canalWorkConfiguration.setExecutorService(executorService);
                })
                .forEach(CanalWorker::start);
        if (!canalWorkers.isEmpty()) {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> canalWorkers.forEach(CanalWorker::stop)));
        }
    }
}
