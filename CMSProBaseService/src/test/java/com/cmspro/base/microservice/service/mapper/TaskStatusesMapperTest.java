package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TaskStatusesMapperTest {

    private TaskStatusesMapper taskStatusesMapper;

    @BeforeEach
    public void setUp() {
        taskStatusesMapper = new TaskStatusesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(taskStatusesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(taskStatusesMapper.fromId(null)).isNull();
    }
}
