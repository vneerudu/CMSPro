package com.cmspro.base.microservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class TaskLocationsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskLocationsDTO.class);
        TaskLocationsDTO taskLocationsDTO1 = new TaskLocationsDTO();
        taskLocationsDTO1.setId("id1");
        TaskLocationsDTO taskLocationsDTO2 = new TaskLocationsDTO();
        assertThat(taskLocationsDTO1).isNotEqualTo(taskLocationsDTO2);
        taskLocationsDTO2.setId(taskLocationsDTO1.getId());
        assertThat(taskLocationsDTO1).isEqualTo(taskLocationsDTO2);
        taskLocationsDTO2.setId("id2");
        assertThat(taskLocationsDTO1).isNotEqualTo(taskLocationsDTO2);
        taskLocationsDTO1.setId(null);
        assertThat(taskLocationsDTO1).isNotEqualTo(taskLocationsDTO2);
    }
}
