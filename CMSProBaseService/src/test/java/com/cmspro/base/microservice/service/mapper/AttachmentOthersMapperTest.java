package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AttachmentOthersMapperTest {

    private AttachmentOthersMapper attachmentOthersMapper;

    @BeforeEach
    public void setUp() {
        attachmentOthersMapper = new AttachmentOthersMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(attachmentOthersMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(attachmentOthersMapper.fromId(null)).isNull();
    }
}
