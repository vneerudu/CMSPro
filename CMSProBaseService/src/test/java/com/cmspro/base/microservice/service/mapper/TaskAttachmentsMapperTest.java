package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TaskAttachmentsMapperTest {

    private TaskAttachmentsMapper taskAttachmentsMapper;

    @BeforeEach
    public void setUp() {
        taskAttachmentsMapper = new TaskAttachmentsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(taskAttachmentsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(taskAttachmentsMapper.fromId(null)).isNull();
    }
}
