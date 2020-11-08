package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SheetsMapperTest {

    private SheetsMapper sheetsMapper;

    @BeforeEach
    public void setUp() {
        sheetsMapper = new SheetsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(sheetsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sheetsMapper.fromId(null)).isNull();
    }
}
