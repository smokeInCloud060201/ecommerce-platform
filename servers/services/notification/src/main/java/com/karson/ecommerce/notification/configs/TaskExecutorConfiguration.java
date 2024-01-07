package com.karson.ecommerce.notification.configs;

import com.karson.ecommerce.common.configs.taks.TaskGlobalExecutorConfiguration;
import com.karson.ecommerce.common.configs.taks.TaskNameProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Configuration
@ConditionalOnProperty(value = "task.pools.enabled", havingValue = "true")
public class TaskExecutorConfiguration extends TaskGlobalExecutorConfiguration {

    public static final String EMAIL_TASK_POOLS = "emailTaskExecutor";

    public TaskExecutorConfiguration(TaskNameProperties taskNameProperties) {
        super(taskNameProperties);
    }

    @Bean(name = EMAIL_TASK_POOLS)
    public ThreadPoolTaskExecutor emailTaskExecutor() {
        if (validateBeanEnabled(EMAIL_TASK_POOLS)) {
            return null;
        }

        return createTaskExecutor(EMAIL_TASK_POOLS, 1, 3, 120, 1000);
    }
}
