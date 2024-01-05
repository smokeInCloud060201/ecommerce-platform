package com.karson.ecommerce.common.configs.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Setter
@Configuration
public class RedisConfig {

    @Value("${cache.redis.host}")
    private String redisHost;
    @Value("${cache.redis.port}")
    private int redisPort;

    @Bean
    @Primary
    public LettuceConnectionFactory  redisConnectionFactory() {
        RedisStandaloneConfiguration redisConnectionFactory = new RedisStandaloneConfiguration();
        redisConnectionFactory.setPort(redisPort);
        redisConnectionFactory.setHostName(redisHost);
        return new LettuceConnectionFactory(redisConnectionFactory);
    }


    @Bean
    @Primary
    public <T> RedisTemplate<String, T> redisTemplate(RedisConnectionFactory connectionFactory) {
        final RedisTemplate<String, T> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        ObjectMapper om = new ObjectMapper();
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.registerModule(new JavaTimeModule());

        template.setKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer(om));
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer(om));
        return template;
    }
}
