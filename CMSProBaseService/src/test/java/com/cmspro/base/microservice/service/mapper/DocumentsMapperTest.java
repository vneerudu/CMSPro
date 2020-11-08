package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DocumentsMapperTest {

    private DocumentsMapper documentsMapper;

    @BeforeEach
    public void setUp() {
        documentsMapper = new DocumentsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(documentsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(documentsMapper.fromId(null)).isNull();
    }
}
