package com.cmspro.basemicroservice.web.rest;

import com.cmspro.basemicroservice.CmsProBaseServiceApp;
import com.cmspro.basemicroservice.service.CmsProBaseServiceKafkaProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EmbeddedKafka
@SpringBootTest(classes = CmsProBaseServiceApp.class)
public class CmsProBaseServiceKafkaResourceIT {

    @Autowired
    private CmsProBaseServiceKafkaProducer kafkaProducer;

    private MockMvc restMockMvc;

    @BeforeEach
    public void setup() {
        CmsProBaseServiceKafkaResource kafkaResource = new CmsProBaseServiceKafkaResource(kafkaProducer);

        this.restMockMvc = MockMvcBuilders.standaloneSetup(kafkaResource)
            .build();
    }

    @Test
    public void sendMessageToKafkaTopic() throws Exception {
        restMockMvc.perform(post("/api/cms-pro-base-service-kafka/publish?message=yolo"))
            .andExpect(status().isOk());
    }
}
