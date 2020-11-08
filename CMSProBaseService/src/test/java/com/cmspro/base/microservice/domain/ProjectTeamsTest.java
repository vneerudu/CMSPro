package com.cmspro.base.microservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class ProjectTeamsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectTeams.class);
        ProjectTeams projectTeams1 = new ProjectTeams();
        projectTeams1.setId("id1");
        ProjectTeams projectTeams2 = new ProjectTeams();
        projectTeams2.setId(projectTeams1.getId());
        assertThat(projectTeams1).isEqualTo(projectTeams2);
        projectTeams2.setId("id2");
        assertThat(projectTeams1).isNotEqualTo(projectTeams2);
        projectTeams1.setId(null);
        assertThat(projectTeams1).isNotEqualTo(projectTeams2);
    }
}
