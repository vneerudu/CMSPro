package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProjectTeamsMapperTest {

    private ProjectTeamsMapper projectTeamsMapper;

    @BeforeEach
    public void setUp() {
        projectTeamsMapper = new ProjectTeamsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(projectTeamsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(projectTeamsMapper.fromId(null)).isNull();
    }
}
