package com.cmspro.base.microservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class TaskAttachmentImagesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskAttachmentImagesDTO.class);
        TaskAttachmentImagesDTO taskAttachmentImagesDTO1 = new TaskAttachmentImagesDTO();
        taskAttachmentImagesDTO1.setId("id1");
        TaskAttachmentImagesDTO taskAttachmentImagesDTO2 = new TaskAttachmentImagesDTO();
        assertThat(taskAttachmentImagesDTO1).isNotEqualTo(taskAttachmentImagesDTO2);
        taskAttachmentImagesDTO2.setId(taskAttachmentImagesDTO1.getId());
        assertThat(taskAttachmentImagesDTO1).isEqualTo(taskAttachmentImagesDTO2);
        taskAttachmentImagesDTO2.setId("id2");
        assertThat(taskAttachmentImagesDTO1).isNotEqualTo(taskAttachmentImagesDTO2);
        taskAttachmentImagesDTO1.setId(null);
        assertThat(taskAttachmentImagesDTO1).isNotEqualTo(taskAttachmentImagesDTO2);
    }
}
