package com.global.common.task.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

import java.util.concurrent.RejectedExecutionHandler;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2021-04-01 下午 02:38
 * @Version: v1.0
 */
@ConditionalOnBean(TaskExecutor.class)
@Configuration
public class DefaultTaskExecutorConfig extends AbstractTaskExecutorConfig {

    @Override
    protected RejectedExecutionHandler rejectedExecutionHandler() {
        return callerRunsPolicy();
    }
}
