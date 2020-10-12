package com.cmspro.basemicroservice.web.rest;

import com.cmspro.basemicroservice.service.CmsProMicroServiceKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/cms-pro-micro-service-kafka")
public class CmsProMicroServiceKafkaResource {

    private final Logger log = LoggerFactory.getLogger(CmsProMicroServiceKafkaResource.class);

    private CmsProMicroServiceKafkaProducer kafkaProducer;

    public CmsProMicroServiceKafkaResource(CmsProMicroServiceKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        log.debug("REST request to send to Kafka topic the message : {}", message);
        this.kafkaProducer.sendMessage(message);
    }
}
