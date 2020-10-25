package com.cmspro.basemicroservice.web.rest;

import com.cmspro.basemicroservice.service.CmsProBaseServiceKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/cms-pro-base-service-kafka")
public class CmsProBaseServiceKafkaResource {

    private final Logger log = LoggerFactory.getLogger(CmsProBaseServiceKafkaResource.class);

    private CmsProBaseServiceKafkaProducer kafkaProducer;

    public CmsProBaseServiceKafkaResource(CmsProBaseServiceKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        log.debug("REST request to send to Kafka topic the message : {}", message);
        this.kafkaProducer.sendMessage(message);
    }
}
