package com.cmspro.base.microservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class TaskCommentsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskComments.class);
        TaskComments taskComments1 = new TaskComments();
        taskComments1.setId("id1");
        TaskComments taskComments2 = new TaskComments();
        taskComments2.setId(taskComments1.getId());
        assertThat(taskComments1).isEqualTo(taskComments2);
        taskComments2.setId("id2");
        assertThat(taskComments1).isNotEqualTo(taskComments2);
        taskComments1.setId(null);
        assertThat(taskComments1).isNotEqualTo(taskComments2);
    }
}
