package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StatesMapperTest {

    private StatesMapper statesMapper;

    @BeforeEach
    public void setUp() {
        statesMapper = new StatesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(statesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(statesMapper.fromId(null)).isNull();
    }
}
