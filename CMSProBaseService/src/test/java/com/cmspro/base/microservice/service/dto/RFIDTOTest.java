package com.cmspro.base.microservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class RFIDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RFIDTO.class);
        RFIDTO rFIDTO1 = new RFIDTO();
        rFIDTO1.setId("id1");
        RFIDTO rFIDTO2 = new RFIDTO();
        assertThat(rFIDTO1).isNotEqualTo(rFIDTO2);
        rFIDTO2.setId(rFIDTO1.getId());
        assertThat(rFIDTO1).isEqualTo(rFIDTO2);
        rFIDTO2.setId("id2");
        assertThat(rFIDTO1).isNotEqualTo(rFIDTO2);
        rFIDTO1.setId(null);
        assertThat(rFIDTO1).isNotEqualTo(rFIDTO2);
    }
}
