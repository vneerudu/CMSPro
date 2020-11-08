package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ListsMapperTest {

    private ListsMapper listsMapper;

    @BeforeEach
    public void setUp() {
        listsMapper = new ListsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(listsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(listsMapper.fromId(null)).isNull();
    }
}
