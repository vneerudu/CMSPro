package com.cmspro.base.microservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class RFITimeLineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RFITimeLineDTO.class);
        RFITimeLineDTO rFITimeLineDTO1 = new RFITimeLineDTO();
        rFITimeLineDTO1.setId("id1");
        RFITimeLineDTO rFITimeLineDTO2 = new RFITimeLineDTO();
        assertThat(rFITimeLineDTO1).isNotEqualTo(rFITimeLineDTO2);
        rFITimeLineDTO2.setId(rFITimeLineDTO1.getId());
        assertThat(rFITimeLineDTO1).isEqualTo(rFITimeLineDTO2);
        rFITimeLineDTO2.setId("id2");
        assertThat(rFITimeLineDTO1).isNotEqualTo(rFITimeLineDTO2);
        rFITimeLineDTO1.setId(null);
        assertThat(rFITimeLineDTO1).isNotEqualTo(rFITimeLineDTO2);
    }
}
