package com.fanxuankai.boot.canal.autoconfigure;

import com.fanxuankai.canal.core.CanalWorker;
import com.fanxuankai.canal.core.config.CanalWorkConfiguration;
import com.fanxuankai.commons.util.concurrent.ThreadPoolService;
import com.fanxuankai.spring.util.ApplicationContexts;
import com.fanxuankai.spring.util.annotation.EnableApplicationContextAware;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author fanxuankai
 */
@EnableApplicationContextAware
public class CanalAutoConfiguration implements ApplicationRunner {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    @Lazy
    private ThreadPoolExecutor threadPoolExecutor;

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean
    public ThreadPoolExecutor threadPoolExecutor() {
        return ThreadPoolService.getInstance();
    }

    @Override
    public void run(ApplicationArguments args) {
        Collection<CanalWorker> canalWorkers =
                ApplicationContexts.getApplicationContext().getBeansOfType(CanalWorker.class).values();
        canalWorkers.stream()
                .peek(canalWorker -> {
                    CanalWorkConfiguration canalWorkConfiguration = canalWorker.getCanalWorkConfiguration();
                    canalWorkConfiguration.setRedisTemplate(redisTemplate);
                    canalWorkConfiguration.setThreadPoolExecutor(threadPoolExecutor);
                })
                .forEach(CanalWorker::start);
        if (!canalWorkers.isEmpty()) {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> canalWorkers.forEach(CanalWorker::stop)));
        }
    }
}
