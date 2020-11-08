package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AttachmentImagesMapperTest {

    private AttachmentImagesMapper attachmentImagesMapper;

    @BeforeEach
    public void setUp() {
        attachmentImagesMapper = new AttachmentImagesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(attachmentImagesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(attachmentImagesMapper.fromId(null)).isNull();
    }
}
