package com.cmspro.base.microservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class AccountStatusesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountStatuses.class);
        AccountStatuses accountStatuses1 = new AccountStatuses();
        accountStatuses1.setId("id1");
        AccountStatuses accountStatuses2 = new AccountStatuses();
        accountStatuses2.setId(accountStatuses1.getId());
        assertThat(accountStatuses1).isEqualTo(accountStatuses2);
        accountStatuses2.setId("id2");
        assertThat(accountStatuses1).isNotEqualTo(accountStatuses2);
        accountStatuses1.setId(null);
        assertThat(accountStatuses1).isNotEqualTo(accountStatuses2);
    }
}
