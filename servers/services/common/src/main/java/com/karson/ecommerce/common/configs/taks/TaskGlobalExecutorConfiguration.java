package com.karson.ecommerce.common.configs.taks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@ConditionalOnProperty(value = "task.pools.enabled", havingValue = "true")
@Slf4j
public class TaskGlobalExecutorConfiguration {
    private final TaskNameProperties taskNameProperties;

    public TaskGlobalExecutorConfiguration(TaskNameProperties taskNameProperties) {
        this.taskNameProperties = taskNameProperties;
    }


    protected boolean validateBeanEnabled(final String beanName) {
        boolean isInvalid = taskNameProperties.getTasksList().stream().noneMatch(b -> b.getName().equalsIgnoreCase(beanName) && b.isEnabled());
        if (isInvalid) {
            log.info("The bean executor {} was not created", beanName);
        }
        return isInvalid;
    }

    protected ThreadPoolTaskExecutor createTaskExecutor(String beanName, int corePoolSize, int maxPoolSize, int aliveTime, RejectedExecutionHandler rejectedExecutionHandler, int queueCapacity) {
        if (rejectedExecutionHandler == null) {
            rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
        }
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setKeepAliveSeconds(aliveTime);
        executor.setRejectedExecutionHandler(rejectedExecutionHandler);
        executor.setQueueCapacity(queueCapacity);
        log.info("The bean executor {} was created with corePoolSize {} maxPoolSize {} aliveTime {} queueCapacity {}", beanName, corePoolSize, maxPoolSize, aliveTime, queueCapacity);
        return executor;
    }

    protected ThreadPoolTaskExecutor createTaskExecutor(String beanName, int corePoolSize, int maxPoolSize, int aliveTime, int queueCapacity) {
        return createTaskExecutor(beanName, corePoolSize, maxPoolSize, aliveTime, null, queueCapacity);
    }


}
