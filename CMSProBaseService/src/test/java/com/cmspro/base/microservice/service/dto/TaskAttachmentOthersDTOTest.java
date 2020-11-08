package com.cmspro.base.microservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class TaskAttachmentOthersDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskAttachmentOthersDTO.class);
        TaskAttachmentOthersDTO taskAttachmentOthersDTO1 = new TaskAttachmentOthersDTO();
        taskAttachmentOthersDTO1.setId("id1");
        TaskAttachmentOthersDTO taskAttachmentOthersDTO2 = new TaskAttachmentOthersDTO();
        assertThat(taskAttachmentOthersDTO1).isNotEqualTo(taskAttachmentOthersDTO2);
        taskAttachmentOthersDTO2.setId(taskAttachmentOthersDTO1.getId());
        assertThat(taskAttachmentOthersDTO1).isEqualTo(taskAttachmentOthersDTO2);
        taskAttachmentOthersDTO2.setId("id2");
        assertThat(taskAttachmentOthersDTO1).isNotEqualTo(taskAttachmentOthersDTO2);
        taskAttachmentOthersDTO1.setId(null);
        assertThat(taskAttachmentOthersDTO1).isNotEqualTo(taskAttachmentOthersDTO2);
    }
}
