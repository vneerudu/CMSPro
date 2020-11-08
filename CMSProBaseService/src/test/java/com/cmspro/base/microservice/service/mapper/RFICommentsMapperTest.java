package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RFICommentsMapperTest {

    private RFICommentsMapper rFICommentsMapper;

    @BeforeEach
    public void setUp() {
        rFICommentsMapper = new RFICommentsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(rFICommentsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(rFICommentsMapper.fromId(null)).isNull();
    }
}
