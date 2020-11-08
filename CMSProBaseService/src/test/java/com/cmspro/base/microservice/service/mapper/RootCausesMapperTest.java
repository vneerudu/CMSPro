package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RootCausesMapperTest {

    private RootCausesMapper rootCausesMapper;

    @BeforeEach
    public void setUp() {
        rootCausesMapper = new RootCausesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(rootCausesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(rootCausesMapper.fromId(null)).isNull();
    }
}
