package com.cmspro.base.microservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class TaskStatusesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskStatusesDTO.class);
        TaskStatusesDTO taskStatusesDTO1 = new TaskStatusesDTO();
        taskStatusesDTO1.setId("id1");
        TaskStatusesDTO taskStatusesDTO2 = new TaskStatusesDTO();
        assertThat(taskStatusesDTO1).isNotEqualTo(taskStatusesDTO2);
        taskStatusesDTO2.setId(taskStatusesDTO1.getId());
        assertThat(taskStatusesDTO1).isEqualTo(taskStatusesDTO2);
        taskStatusesDTO2.setId("id2");
        assertThat(taskStatusesDTO1).isNotEqualTo(taskStatusesDTO2);
        taskStatusesDTO1.setId(null);
        assertThat(taskStatusesDTO1).isNotEqualTo(taskStatusesDTO2);
    }
}
