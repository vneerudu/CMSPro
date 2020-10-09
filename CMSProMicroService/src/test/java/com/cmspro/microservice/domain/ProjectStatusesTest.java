package com.cmspro.microservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.microservice.web.rest.TestUtil;

public class ProjectStatusesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectStatuses.class);
        ProjectStatuses projectStatuses1 = new ProjectStatuses();
        projectStatuses1.setId(1L);
        ProjectStatuses projectStatuses2 = new ProjectStatuses();
        projectStatuses2.setId(projectStatuses1.getId());
        assertThat(projectStatuses1).isEqualTo(projectStatuses2);
        projectStatuses2.setId(2L);
        assertThat(projectStatuses1).isNotEqualTo(projectStatuses2);
        projectStatuses1.setId(null);
        assertThat(projectStatuses1).isNotEqualTo(projectStatuses2);
    }
}
