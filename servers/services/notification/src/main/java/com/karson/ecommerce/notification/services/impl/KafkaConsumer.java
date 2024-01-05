package com.karson.ecommerce.notification.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {
    private static final String TOPIC_NAME = "example-topic";


    @KafkaListener(topics = TOPIC_NAME, groupId = "${kafka.group-id-config}")
    public void listen(String message) {
        log.info(message);
    }
}
