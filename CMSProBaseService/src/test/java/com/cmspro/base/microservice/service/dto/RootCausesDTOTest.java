package com.cmspro.base.microservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class RootCausesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RootCausesDTO.class);
        RootCausesDTO rootCausesDTO1 = new RootCausesDTO();
        rootCausesDTO1.setId("id1");
        RootCausesDTO rootCausesDTO2 = new RootCausesDTO();
        assertThat(rootCausesDTO1).isNotEqualTo(rootCausesDTO2);
        rootCausesDTO2.setId(rootCausesDTO1.getId());
        assertThat(rootCausesDTO1).isEqualTo(rootCausesDTO2);
        rootCausesDTO2.setId("id2");
        assertThat(rootCausesDTO1).isNotEqualTo(rootCausesDTO2);
        rootCausesDTO1.setId(null);
        assertThat(rootCausesDTO1).isNotEqualTo(rootCausesDTO2);
    }
}
