package com.cmspro.base.microservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class TaskAttachmentOthersTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskAttachmentOthers.class);
        TaskAttachmentOthers taskAttachmentOthers1 = new TaskAttachmentOthers();
        taskAttachmentOthers1.setId("id1");
        TaskAttachmentOthers taskAttachmentOthers2 = new TaskAttachmentOthers();
        taskAttachmentOthers2.setId(taskAttachmentOthers1.getId());
        assertThat(taskAttachmentOthers1).isEqualTo(taskAttachmentOthers2);
        taskAttachmentOthers2.setId("id2");
        assertThat(taskAttachmentOthers1).isNotEqualTo(taskAttachmentOthers2);
        taskAttachmentOthers1.setId(null);
        assertThat(taskAttachmentOthers1).isNotEqualTo(taskAttachmentOthers2);
    }
}
