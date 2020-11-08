package com.cmspro.base.microservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class TaskLocationsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskLocations.class);
        TaskLocations taskLocations1 = new TaskLocations();
        taskLocations1.setId("id1");
        TaskLocations taskLocations2 = new TaskLocations();
        taskLocations2.setId(taskLocations1.getId());
        assertThat(taskLocations1).isEqualTo(taskLocations2);
        taskLocations2.setId("id2");
        assertThat(taskLocations1).isNotEqualTo(taskLocations2);
        taskLocations1.setId(null);
        assertThat(taskLocations1).isNotEqualTo(taskLocations2);
    }
}
