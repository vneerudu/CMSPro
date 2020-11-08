package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StampsMapperTest {

    private StampsMapper stampsMapper;

    @BeforeEach
    public void setUp() {
        stampsMapper = new StampsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(stampsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(stampsMapper.fromId(null)).isNull();
    }
}
