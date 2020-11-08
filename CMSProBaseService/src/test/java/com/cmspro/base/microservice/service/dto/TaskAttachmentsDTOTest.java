package com.cmspro.base.microservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class TaskAttachmentsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskAttachmentsDTO.class);
        TaskAttachmentsDTO taskAttachmentsDTO1 = new TaskAttachmentsDTO();
        taskAttachmentsDTO1.setId("id1");
        TaskAttachmentsDTO taskAttachmentsDTO2 = new TaskAttachmentsDTO();
        assertThat(taskAttachmentsDTO1).isNotEqualTo(taskAttachmentsDTO2);
        taskAttachmentsDTO2.setId(taskAttachmentsDTO1.getId());
        assertThat(taskAttachmentsDTO1).isEqualTo(taskAttachmentsDTO2);
        taskAttachmentsDTO2.setId("id2");
        assertThat(taskAttachmentsDTO1).isNotEqualTo(taskAttachmentsDTO2);
        taskAttachmentsDTO1.setId(null);
        assertThat(taskAttachmentsDTO1).isNotEqualTo(taskAttachmentsDTO2);
    }
}
