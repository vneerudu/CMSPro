package com.cmspro.base.microservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class RootCauseGroupsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RootCauseGroupsDTO.class);
        RootCauseGroupsDTO rootCauseGroupsDTO1 = new RootCauseGroupsDTO();
        rootCauseGroupsDTO1.setId("id1");
        RootCauseGroupsDTO rootCauseGroupsDTO2 = new RootCauseGroupsDTO();
        assertThat(rootCauseGroupsDTO1).isNotEqualTo(rootCauseGroupsDTO2);
        rootCauseGroupsDTO2.setId(rootCauseGroupsDTO1.getId());
        assertThat(rootCauseGroupsDTO1).isEqualTo(rootCauseGroupsDTO2);
        rootCauseGroupsDTO2.setId("id2");
        assertThat(rootCauseGroupsDTO1).isNotEqualTo(rootCauseGroupsDTO2);
        rootCauseGroupsDTO1.setId(null);
        assertThat(rootCauseGroupsDTO1).isNotEqualTo(rootCauseGroupsDTO2);
    }
}
