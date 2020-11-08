package com.cmspro.base.microservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class TasksDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TasksDTO.class);
        TasksDTO tasksDTO1 = new TasksDTO();
        tasksDTO1.setId("id1");
        TasksDTO tasksDTO2 = new TasksDTO();
        assertThat(tasksDTO1).isNotEqualTo(tasksDTO2);
        tasksDTO2.setId(tasksDTO1.getId());
        assertThat(tasksDTO1).isEqualTo(tasksDTO2);
        tasksDTO2.setId("id2");
        assertThat(tasksDTO1).isNotEqualTo(tasksDTO2);
        tasksDTO1.setId(null);
        assertThat(tasksDTO1).isNotEqualTo(tasksDTO2);
    }
}
