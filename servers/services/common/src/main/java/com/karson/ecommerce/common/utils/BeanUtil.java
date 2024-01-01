package com.karson.ecommerce.common.utils;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

public class BeanUtil {
    private BeanUtil() {}

    public static void removeBeans( List<String> beansNameRemove, ConfigurableApplicationContext context)  {
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) context.getAutowireCapableBeanFactory();
        for (String s : context.getBeanDefinitionNames()) {
            if (beansNameRemove.contains(s)) {
                registry.removeBeanDefinition(s);
            }
        }
    }
}
