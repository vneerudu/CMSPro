package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RootCauseGroupsMapperTest {

    private RootCauseGroupsMapper rootCauseGroupsMapper;

    @BeforeEach
    public void setUp() {
        rootCauseGroupsMapper = new RootCauseGroupsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(rootCauseGroupsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(rootCauseGroupsMapper.fromId(null)).isNull();
    }
}
