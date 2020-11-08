package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RFIMapperTest {

    private RFIMapper rFIMapper;

    @BeforeEach
    public void setUp() {
        rFIMapper = new RFIMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(rFIMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(rFIMapper.fromId(null)).isNull();
    }
}
