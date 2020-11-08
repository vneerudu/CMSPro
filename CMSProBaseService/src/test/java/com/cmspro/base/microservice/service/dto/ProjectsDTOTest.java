package com.cmspro.base.microservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class ProjectsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectsDTO.class);
        ProjectsDTO projectsDTO1 = new ProjectsDTO();
        projectsDTO1.setId("id1");
        ProjectsDTO projectsDTO2 = new ProjectsDTO();
        assertThat(projectsDTO1).isNotEqualTo(projectsDTO2);
        projectsDTO2.setId(projectsDTO1.getId());
        assertThat(projectsDTO1).isEqualTo(projectsDTO2);
        projectsDTO2.setId("id2");
        assertThat(projectsDTO1).isNotEqualTo(projectsDTO2);
        projectsDTO1.setId(null);
        assertThat(projectsDTO1).isNotEqualTo(projectsDTO2);
    }
}
