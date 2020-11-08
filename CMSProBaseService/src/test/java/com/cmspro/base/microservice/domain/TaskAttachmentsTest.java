package com.cmspro.base.microservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class TaskAttachmentsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskAttachments.class);
        TaskAttachments taskAttachments1 = new TaskAttachments();
        taskAttachments1.setId("id1");
        TaskAttachments taskAttachments2 = new TaskAttachments();
        taskAttachments2.setId(taskAttachments1.getId());
        assertThat(taskAttachments1).isEqualTo(taskAttachments2);
        taskAttachments2.setId("id2");
        assertThat(taskAttachments1).isNotEqualTo(taskAttachments2);
        taskAttachments1.setId(null);
        assertThat(taskAttachments1).isNotEqualTo(taskAttachments2);
    }
}
