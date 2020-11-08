package com.cmspro.base.microservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class UserRolesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserRolesDTO.class);
        UserRolesDTO userRolesDTO1 = new UserRolesDTO();
        userRolesDTO1.setId("id1");
        UserRolesDTO userRolesDTO2 = new UserRolesDTO();
        assertThat(userRolesDTO1).isNotEqualTo(userRolesDTO2);
        userRolesDTO2.setId(userRolesDTO1.getId());
        assertThat(userRolesDTO1).isEqualTo(userRolesDTO2);
        userRolesDTO2.setId("id2");
        assertThat(userRolesDTO1).isNotEqualTo(userRolesDTO2);
        userRolesDTO1.setId(null);
        assertThat(userRolesDTO1).isNotEqualTo(userRolesDTO2);
    }
}
