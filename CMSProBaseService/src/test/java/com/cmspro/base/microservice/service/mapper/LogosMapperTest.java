package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LogosMapperTest {

    private LogosMapper logosMapper;

    @BeforeEach
    public void setUp() {
        logosMapper = new LogosMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(logosMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(logosMapper.fromId(null)).isNull();
    }
}
