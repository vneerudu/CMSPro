package com.cmspro.basemicroservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CmsProBaseServiceKafkaConsumer {

    private final Logger log = LoggerFactory.getLogger(CmsProBaseServiceKafkaConsumer.class);
    private static final String TOPIC = "topic_cmsprobaseservice";

    @KafkaListener(topics = "topic_cmsprobaseservice", groupId = "group_id")
    public void consume(String message) throws IOException {
        log.info("Consumed message in {} : {}", TOPIC, message);
    }
}
