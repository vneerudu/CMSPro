package com.cmspro.basemicroservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CmsProMicroServiceKafkaConsumer {

    private final Logger log = LoggerFactory.getLogger(CmsProMicroServiceKafkaConsumer.class);
    private static final String TOPIC = "topic_cmspromicroservice";

    //@KafkaListener(topics = "topic_cmspromicroservice", groupId = "group_id")
    public void consume(String message) throws IOException {
        log.info("Consumed message in {} : {}", TOPIC, message);
    }
}
