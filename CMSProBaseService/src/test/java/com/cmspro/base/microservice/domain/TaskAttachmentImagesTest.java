package com.cmspro.base.microservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class TaskAttachmentImagesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskAttachmentImages.class);
        TaskAttachmentImages taskAttachmentImages1 = new TaskAttachmentImages();
        taskAttachmentImages1.setId("id1");
        TaskAttachmentImages taskAttachmentImages2 = new TaskAttachmentImages();
        taskAttachmentImages2.setId(taskAttachmentImages1.getId());
        assertThat(taskAttachmentImages1).isEqualTo(taskAttachmentImages2);
        taskAttachmentImages2.setId("id2");
        assertThat(taskAttachmentImages1).isNotEqualTo(taskAttachmentImages2);
        taskAttachmentImages1.setId(null);
        assertThat(taskAttachmentImages1).isNotEqualTo(taskAttachmentImages2);
    }
}
