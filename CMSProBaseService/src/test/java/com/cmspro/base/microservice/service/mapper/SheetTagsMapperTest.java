package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SheetTagsMapperTest {

    private SheetTagsMapper sheetTagsMapper;

    @BeforeEach
    public void setUp() {
        sheetTagsMapper = new SheetTagsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(sheetTagsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sheetTagsMapper.fromId(null)).isNull();
    }
}
