package com.global.common.task.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-04-02 上午 10:50
 * @Version: v1.0
 */
@Slf4j
public abstract class AbstractTaskExecutorConfig {

    public AbstractTaskExecutorConfig() {
        this.corePoolSize = 1;
        this.maxPoolSize = 10;
        this.keepAliveSeconds = 6000;
    }

    // 核心线程数，线程池维护线程的最少数量，哪怕是空闲的。
    @Value(value = "${global.task.executor.corePoolSize:}")
    private Integer corePoolSize;
    // 线程池维护线程的最大数量。
    @Value(value = "${global.task.executor.maxPoolSize:}")
    private Integer maxPoolSize;
    // 线程池维护线程所允许的空闲时间，秒。
    @Value(value = "${global.task.executor.keepAliveSeconds:}")
    private Integer keepAliveSeconds;

    protected abstract RejectedExecutionHandler rejectedExecutionHandler();

    /**
     * 创建线程池, 默认配置, 默认访问策略
     */
    @Bean
    @ConditionalOnBean(TaskExecutor.class)
    public TaskExecutor executor() {
        return executor(corePoolSize, maxPoolSize, keepAliveSeconds, rejectedExecutionHandler()); // 设置线程池拒绝策略
    }

    /**
     * 创建线程池
     */
    private TaskExecutor executor(final int corePoolSize, final int maxPoolSize, final int keepAliveSeconds, RejectedExecutionHandler executionHandler) {
        log.info("corePoolSize: {}, maxPoolSize: {}, keepAliveSeconds: {}", corePoolSize, maxPoolSize, keepAliveSeconds);
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setRejectedExecutionHandler(executionHandler); // 设置线程池拒绝策略
        return executor;
    }

    /**
     * 这个策略重试添加当前的任务，他会自动重复调用 execute() 方法，直到成功。
     * @return 线程池拒绝策略
     */
    protected RejectedExecutionHandler callerRunsPolicy() {
        return new ThreadPoolExecutor.CallerRunsPolicy();
    }

    /**
     * 对拒绝任务抛弃处理，并且抛出异常。
     * @return 线程池拒绝策略
     */
    protected RejectedExecutionHandler abortPolicy() {
        return new ThreadPoolExecutor.AbortPolicy();
    }

    /**
     * 对拒绝任务直接无声抛弃，没有异常信息。
     * @return 线程池拒绝策略
     */
    protected RejectedExecutionHandler discardPolicy() {
        return new ThreadPoolExecutor.DiscardPolicy();
    }

    /**
     * 对拒绝任务不抛弃，而是抛弃队列里面等待最久的一个线程，然后把拒绝任务加到队列。
     * @return 线程池拒绝策略
     */
    protected RejectedExecutionHandler discardOldestPolicy() {
        return new ThreadPoolExecutor.DiscardOldestPolicy();
    }
}
