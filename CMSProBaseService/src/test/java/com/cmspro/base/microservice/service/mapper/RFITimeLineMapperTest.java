package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RFITimeLineMapperTest {

    private RFITimeLineMapper rFITimeLineMapper;

    @BeforeEach
    public void setUp() {
        rFITimeLineMapper = new RFITimeLineMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(rFITimeLineMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(rFITimeLineMapper.fromId(null)).isNull();
    }
}
