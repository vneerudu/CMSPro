package com.cmspro.base.microservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class TaskStatusesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskStatuses.class);
        TaskStatuses taskStatuses1 = new TaskStatuses();
        taskStatuses1.setId("id1");
        TaskStatuses taskStatuses2 = new TaskStatuses();
        taskStatuses2.setId(taskStatuses1.getId());
        assertThat(taskStatuses1).isEqualTo(taskStatuses2);
        taskStatuses2.setId("id2");
        assertThat(taskStatuses1).isNotEqualTo(taskStatuses2);
        taskStatuses1.setId(null);
        assertThat(taskStatuses1).isNotEqualTo(taskStatuses2);
    }
}
