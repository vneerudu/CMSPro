package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TasksMapperTest {

    private TasksMapper tasksMapper;

    @BeforeEach
    public void setUp() {
        tasksMapper = new TasksMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(tasksMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tasksMapper.fromId(null)).isNull();
    }
}
