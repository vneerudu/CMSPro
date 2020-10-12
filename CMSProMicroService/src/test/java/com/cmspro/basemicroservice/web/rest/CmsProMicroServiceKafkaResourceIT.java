package com.cmspro.basemicroservice.web.rest;

import com.cmspro.basemicroservice.CmsProMicroServiceApp;
import com.cmspro.basemicroservice.service.CmsProMicroServiceKafkaProducer;
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
@SpringBootTest(classes = CmsProMicroServiceApp.class)
public class CmsProMicroServiceKafkaResourceIT {

    @Autowired
    private CmsProMicroServiceKafkaProducer kafkaProducer;

    private MockMvc restMockMvc;

    @BeforeEach
    public void setup() {
        CmsProMicroServiceKafkaResource kafkaResource = new CmsProMicroServiceKafkaResource(kafkaProducer);

        this.restMockMvc = MockMvcBuilders.standaloneSetup(kafkaResource)
            .build();
    }

    @Test
    public void sendMessageToKafkaTopic() throws Exception {
        restMockMvc.perform(post("/api/cms-pro-micro-service-kafka/publish?message=yolo"))
            .andExpect(status().isOk());
    }
}
