package com.cmspro.base.microservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class ProjectTeamsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectTeamsDTO.class);
        ProjectTeamsDTO projectTeamsDTO1 = new ProjectTeamsDTO();
        projectTeamsDTO1.setId("id1");
        ProjectTeamsDTO projectTeamsDTO2 = new ProjectTeamsDTO();
        assertThat(projectTeamsDTO1).isNotEqualTo(projectTeamsDTO2);
        projectTeamsDTO2.setId(projectTeamsDTO1.getId());
        assertThat(projectTeamsDTO1).isEqualTo(projectTeamsDTO2);
        projectTeamsDTO2.setId("id2");
        assertThat(projectTeamsDTO1).isNotEqualTo(projectTeamsDTO2);
        projectTeamsDTO1.setId(null);
        assertThat(projectTeamsDTO1).isNotEqualTo(projectTeamsDTO2);
    }
}
