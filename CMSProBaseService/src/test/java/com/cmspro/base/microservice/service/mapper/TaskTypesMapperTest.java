package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TaskTypesMapperTest {

    private TaskTypesMapper taskTypesMapper;

    @BeforeEach
    public void setUp() {
        taskTypesMapper = new TaskTypesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(taskTypesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(taskTypesMapper.fromId(null)).isNull();
    }
}
