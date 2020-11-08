package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LanguagesMapperTest {

    private LanguagesMapper languagesMapper;

    @BeforeEach
    public void setUp() {
        languagesMapper = new LanguagesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(languagesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(languagesMapper.fromId(null)).isNull();
    }
}
