package com.cmspro.base.microservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class RFIStatusesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RFIStatusesDTO.class);
        RFIStatusesDTO rFIStatusesDTO1 = new RFIStatusesDTO();
        rFIStatusesDTO1.setId("id1");
        RFIStatusesDTO rFIStatusesDTO2 = new RFIStatusesDTO();
        assertThat(rFIStatusesDTO1).isNotEqualTo(rFIStatusesDTO2);
        rFIStatusesDTO2.setId(rFIStatusesDTO1.getId());
        assertThat(rFIStatusesDTO1).isEqualTo(rFIStatusesDTO2);
        rFIStatusesDTO2.setId("id2");
        assertThat(rFIStatusesDTO1).isNotEqualTo(rFIStatusesDTO2);
        rFIStatusesDTO1.setId(null);
        assertThat(rFIStatusesDTO1).isNotEqualTo(rFIStatusesDTO2);
    }
}
