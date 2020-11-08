package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TaskLocationsMapperTest {

    private TaskLocationsMapper taskLocationsMapper;

    @BeforeEach
    public void setUp() {
        taskLocationsMapper = new TaskLocationsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(taskLocationsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(taskLocationsMapper.fromId(null)).isNull();
    }
}
