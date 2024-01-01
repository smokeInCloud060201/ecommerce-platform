package com.karson.ecommerce.common.utils;

import com.karson.ecommerce.common.anotations.SearchAllForKey;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

@Component
@Slf4j
public class SearchUtil {
    public <T> boolean filterByKey(String keySearchAll, T object) {
        if (StringUtils.isBlank(keySearchAll)) return true;
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(SearchAllForKey.class)) {
                try {
                    Object fieldValue = new PropertyDescriptor(field.getName(), object.getClass()).getReadMethod().invoke(object);
                    if (isContainKey(keySearchAll, (String) fieldValue)) return true;
                } catch (IllegalAccessException | IntrospectionException | InvocationTargetException e) {
                    log.info("Error when filterByKey message: {}", e.getMessage());
                }
            }
        }
        return false;
    }

    private boolean isContainKey(String keySearchAll, String key) {
        return key.toLowerCase().contains(keySearchAll.toLowerCase());
    }
}
