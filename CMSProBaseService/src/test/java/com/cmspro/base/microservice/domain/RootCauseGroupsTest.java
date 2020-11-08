package com.cmspro.base.microservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class RootCauseGroupsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RootCauseGroups.class);
        RootCauseGroups rootCauseGroups1 = new RootCauseGroups();
        rootCauseGroups1.setId("id1");
        RootCauseGroups rootCauseGroups2 = new RootCauseGroups();
        rootCauseGroups2.setId(rootCauseGroups1.getId());
        assertThat(rootCauseGroups1).isEqualTo(rootCauseGroups2);
        rootCauseGroups2.setId("id2");
        assertThat(rootCauseGroups1).isNotEqualTo(rootCauseGroups2);
        rootCauseGroups1.setId(null);
        assertThat(rootCauseGroups1).isNotEqualTo(rootCauseGroups2);
    }
}
