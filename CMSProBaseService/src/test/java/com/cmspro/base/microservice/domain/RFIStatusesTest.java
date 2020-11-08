package com.cmspro.base.microservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class RFIStatusesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RFIStatuses.class);
        RFIStatuses rFIStatuses1 = new RFIStatuses();
        rFIStatuses1.setId("id1");
        RFIStatuses rFIStatuses2 = new RFIStatuses();
        rFIStatuses2.setId(rFIStatuses1.getId());
        assertThat(rFIStatuses1).isEqualTo(rFIStatuses2);
        rFIStatuses2.setId("id2");
        assertThat(rFIStatuses1).isNotEqualTo(rFIStatuses2);
        rFIStatuses1.setId(null);
        assertThat(rFIStatuses1).isNotEqualTo(rFIStatuses2);
    }
}
