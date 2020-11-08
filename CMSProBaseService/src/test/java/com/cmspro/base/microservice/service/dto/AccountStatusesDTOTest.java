package com.cmspro.base.microservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class AccountStatusesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountStatusesDTO.class);
        AccountStatusesDTO accountStatusesDTO1 = new AccountStatusesDTO();
        accountStatusesDTO1.setId("id1");
        AccountStatusesDTO accountStatusesDTO2 = new AccountStatusesDTO();
        assertThat(accountStatusesDTO1).isNotEqualTo(accountStatusesDTO2);
        accountStatusesDTO2.setId(accountStatusesDTO1.getId());
        assertThat(accountStatusesDTO1).isEqualTo(accountStatusesDTO2);
        accountStatusesDTO2.setId("id2");
        assertThat(accountStatusesDTO1).isNotEqualTo(accountStatusesDTO2);
        accountStatusesDTO1.setId(null);
        assertThat(accountStatusesDTO1).isNotEqualTo(accountStatusesDTO2);
    }
}
