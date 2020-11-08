package com.cmspro.base.microservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class RFICommentsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RFICommentsDTO.class);
        RFICommentsDTO rFICommentsDTO1 = new RFICommentsDTO();
        rFICommentsDTO1.setId("id1");
        RFICommentsDTO rFICommentsDTO2 = new RFICommentsDTO();
        assertThat(rFICommentsDTO1).isNotEqualTo(rFICommentsDTO2);
        rFICommentsDTO2.setId(rFICommentsDTO1.getId());
        assertThat(rFICommentsDTO1).isEqualTo(rFICommentsDTO2);
        rFICommentsDTO2.setId("id2");
        assertThat(rFICommentsDTO1).isNotEqualTo(rFICommentsDTO2);
        rFICommentsDTO1.setId(null);
        assertThat(rFICommentsDTO1).isNotEqualTo(rFICommentsDTO2);
    }
}
