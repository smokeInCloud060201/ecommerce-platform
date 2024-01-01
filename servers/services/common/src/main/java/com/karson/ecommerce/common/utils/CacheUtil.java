package com.karson.ecommerce.common.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class CacheUtil<K, V> {

    private final RedisTemplate<K, V> redisTemplate;
    private final SearchUtil searchUtil;
    public V getValueByCacheKey(K key) {
        V value = redisTemplate.opsForValue().get(key);
        log.info("Get cache by key {} with value {}", key, value);
        return value;
    }

    public void storeValueByCacheKey(K key, V value) {
        redisTemplate.opsForValue().set(key, value);
        log.info("Store cache by key {} with value {}", key, value);
    }

    public V getListValueByCacheKey(K key, String keySearch) {
        V value = Objects.requireNonNull(redisTemplate.opsForList().range(key, 0, -1)).stream()
                .filter(s -> searchUtil.filterByKey(keySearch, s))
                .findFirst()
                .orElse(null);
        log.info("Get cache by key {} with value {}", key, value);
        return value;
    }

    public void storeListValueByCacheKey(K key, V value) {
        redisTemplate.opsForList().rightPush(key, value);
        log.info("Store cache by key {} with value {}", key, value);
    }
}
