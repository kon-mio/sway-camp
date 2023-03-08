package com.zxy.swaycamp.config;

/**
 * @author XinYuan Zhao
 * @since 2023/3/8
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步线程配置：确保执行方法和异步执行的方法不在同一个类
 */
@Configuration
public class AsyncConfig {
    @Value("${spring.task.execution.pool.core-size}")
    private int corePoolSize;
    @Value("${spring.task.execution.pool.max-size}")
    private int maxPoolSize;
    @Value("${spring.task.execution.pool.queue-capacity}")
    private int queueCapacity;
    @Value("${spring.task.execution.thread-name-prefix}")
    private String threadNamePrefix;
    @Value("${spring.task.execution.pool.keep-alive}")
    private int keepAliveSeconds;

    @Bean("LogAsync")
    public Executor logAsync() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        /*
          拒绝处理策略
          CallerRunsPolicy()：交由调用方线程运行，比如 main 线程。
          AbortPolicy()：直接抛出异常。
          DiscardPolicy()：直接丢弃。
          DiscardOldestPolicy()：丢弃队列中最老的任务。
         */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.initialize();
        return executor;
    }
}