package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RFIStatusesMapperTest {

    private RFIStatusesMapper rFIStatusesMapper;

    @BeforeEach
    public void setUp() {
        rFIStatusesMapper = new RFIStatusesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(rFIStatusesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(rFIStatusesMapper.fromId(null)).isNull();
    }
}
