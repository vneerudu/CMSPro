package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TaskAttachmentImagesMapperTest {

    private TaskAttachmentImagesMapper taskAttachmentImagesMapper;

    @BeforeEach
    public void setUp() {
        taskAttachmentImagesMapper = new TaskAttachmentImagesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(taskAttachmentImagesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(taskAttachmentImagesMapper.fromId(null)).isNull();
    }
}
