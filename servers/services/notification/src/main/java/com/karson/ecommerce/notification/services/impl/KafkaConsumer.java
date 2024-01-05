package com.karson.ecommerce.notification.services.impl;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {
    private static final String TOPIC_NAME = "example-topic";

    @KafkaListener(topics = TOPIC_NAME, groupId = "group-id")
    public void listen(String message) {
        log.info(message);
    }
}
