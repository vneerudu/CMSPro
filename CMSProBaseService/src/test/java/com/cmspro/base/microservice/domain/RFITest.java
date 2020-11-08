package com.cmspro.base.microservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class RFITest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RFI.class);
        RFI rFI1 = new RFI();
        rFI1.setId("id1");
        RFI rFI2 = new RFI();
        rFI2.setId(rFI1.getId());
        assertThat(rFI1).isEqualTo(rFI2);
        rFI2.setId("id2");
        assertThat(rFI1).isNotEqualTo(rFI2);
        rFI1.setId(null);
        assertThat(rFI1).isNotEqualTo(rFI2);
    }
}
