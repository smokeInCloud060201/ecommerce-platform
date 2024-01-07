package com.karson.ecommerce.common.configs.taks;

import lombok.Getter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(value = "task.pools", ignoreInvalidFields = true)
@ConditionalOnProperty(value = "task.pools.enabled", havingValue = "true")
@Component
@Getter
public class TaskNameProperties {
    List<TaskDto> tasksList = new ArrayList<>();
}
