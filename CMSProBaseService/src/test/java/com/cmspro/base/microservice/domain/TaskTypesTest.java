package com.cmspro.base.microservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class TaskTypesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskTypes.class);
        TaskTypes taskTypes1 = new TaskTypes();
        taskTypes1.setId("id1");
        TaskTypes taskTypes2 = new TaskTypes();
        taskTypes2.setId(taskTypes1.getId());
        assertThat(taskTypes1).isEqualTo(taskTypes2);
        taskTypes2.setId("id2");
        assertThat(taskTypes1).isNotEqualTo(taskTypes2);
        taskTypes1.setId(null);
        assertThat(taskTypes1).isNotEqualTo(taskTypes2);
    }
}
