package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TaskCommentsMapperTest {

    private TaskCommentsMapper taskCommentsMapper;

    @BeforeEach
    public void setUp() {
        taskCommentsMapper = new TaskCommentsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(taskCommentsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(taskCommentsMapper.fromId(null)).isNull();
    }
}
