package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TaskAttachmentOthersMapperTest {

    private TaskAttachmentOthersMapper taskAttachmentOthersMapper;

    @BeforeEach
    public void setUp() {
        taskAttachmentOthersMapper = new TaskAttachmentOthersMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(taskAttachmentOthersMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(taskAttachmentOthersMapper.fromId(null)).isNull();
    }
}
