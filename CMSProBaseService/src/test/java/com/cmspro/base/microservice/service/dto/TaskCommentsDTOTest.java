package com.cmspro.base.microservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class TaskCommentsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskCommentsDTO.class);
        TaskCommentsDTO taskCommentsDTO1 = new TaskCommentsDTO();
        taskCommentsDTO1.setId("id1");
        TaskCommentsDTO taskCommentsDTO2 = new TaskCommentsDTO();
        assertThat(taskCommentsDTO1).isNotEqualTo(taskCommentsDTO2);
        taskCommentsDTO2.setId(taskCommentsDTO1.getId());
        assertThat(taskCommentsDTO1).isEqualTo(taskCommentsDTO2);
        taskCommentsDTO2.setId("id2");
        assertThat(taskCommentsDTO1).isNotEqualTo(taskCommentsDTO2);
        taskCommentsDTO1.setId(null);
        assertThat(taskCommentsDTO1).isNotEqualTo(taskCommentsDTO2);
    }
}
