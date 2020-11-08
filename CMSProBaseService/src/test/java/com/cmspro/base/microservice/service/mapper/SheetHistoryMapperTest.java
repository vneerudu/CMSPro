package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SheetHistoryMapperTest {

    private SheetHistoryMapper sheetHistoryMapper;

    @BeforeEach
    public void setUp() {
        sheetHistoryMapper = new SheetHistoryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(sheetHistoryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sheetHistoryMapper.fromId(null)).isNull();
    }
}
