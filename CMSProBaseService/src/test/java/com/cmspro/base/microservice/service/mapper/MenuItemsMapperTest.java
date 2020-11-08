package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MenuItemsMapperTest {

    private MenuItemsMapper menuItemsMapper;

    @BeforeEach
    public void setUp() {
        menuItemsMapper = new MenuItemsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(menuItemsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(menuItemsMapper.fromId(null)).isNull();
    }
}
