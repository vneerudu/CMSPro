package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProjectsMapperTest {

    private ProjectsMapper projectsMapper;

    @BeforeEach
    public void setUp() {
        projectsMapper = new ProjectsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(projectsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(projectsMapper.fromId(null)).isNull();
    }
}
