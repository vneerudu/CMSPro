package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SheetCommentsMapperTest {

    private SheetCommentsMapper sheetCommentsMapper;

    @BeforeEach
    public void setUp() {
        sheetCommentsMapper = new SheetCommentsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(sheetCommentsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sheetCommentsMapper.fromId(null)).isNull();
    }
}
