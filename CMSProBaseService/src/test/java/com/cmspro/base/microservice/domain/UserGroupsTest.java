package com.cmspro.base.microservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class UserGroupsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserGroups.class);
        UserGroups userGroups1 = new UserGroups();
        userGroups1.setId("id1");
        UserGroups userGroups2 = new UserGroups();
        userGroups2.setId(userGroups1.getId());
        assertThat(userGroups1).isEqualTo(userGroups2);
        userGroups2.setId("id2");
        assertThat(userGroups1).isNotEqualTo(userGroups2);
        userGroups1.setId(null);
        assertThat(userGroups1).isNotEqualTo(userGroups2);
    }
}
