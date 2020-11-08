package com.cmspro.base.microservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class UserPermissionsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserPermissionsDTO.class);
        UserPermissionsDTO userPermissionsDTO1 = new UserPermissionsDTO();
        userPermissionsDTO1.setId("id1");
        UserPermissionsDTO userPermissionsDTO2 = new UserPermissionsDTO();
        assertThat(userPermissionsDTO1).isNotEqualTo(userPermissionsDTO2);
        userPermissionsDTO2.setId(userPermissionsDTO1.getId());
        assertThat(userPermissionsDTO1).isEqualTo(userPermissionsDTO2);
        userPermissionsDTO2.setId("id2");
        assertThat(userPermissionsDTO1).isNotEqualTo(userPermissionsDTO2);
        userPermissionsDTO1.setId(null);
        assertThat(userPermissionsDTO1).isNotEqualTo(userPermissionsDTO2);
    }
}
