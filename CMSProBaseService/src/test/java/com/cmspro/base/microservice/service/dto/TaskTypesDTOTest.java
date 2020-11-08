package com.cmspro.base.microservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class TaskTypesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskTypesDTO.class);
        TaskTypesDTO taskTypesDTO1 = new TaskTypesDTO();
        taskTypesDTO1.setId("id1");
        TaskTypesDTO taskTypesDTO2 = new TaskTypesDTO();
        assertThat(taskTypesDTO1).isNotEqualTo(taskTypesDTO2);
        taskTypesDTO2.setId(taskTypesDTO1.getId());
        assertThat(taskTypesDTO1).isEqualTo(taskTypesDTO2);
        taskTypesDTO2.setId("id2");
        assertThat(taskTypesDTO1).isNotEqualTo(taskTypesDTO2);
        taskTypesDTO1.setId(null);
        assertThat(taskTypesDTO1).isNotEqualTo(taskTypesDTO2);
    }
}
