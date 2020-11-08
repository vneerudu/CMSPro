package com.cmspro.base.microservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class StampsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StampsDTO.class);
        StampsDTO stampsDTO1 = new StampsDTO();
        stampsDTO1.setId("id1");
        StampsDTO stampsDTO2 = new StampsDTO();
        assertThat(stampsDTO1).isNotEqualTo(stampsDTO2);
        stampsDTO2.setId(stampsDTO1.getId());
        assertThat(stampsDTO1).isEqualTo(stampsDTO2);
        stampsDTO2.setId("id2");
        assertThat(stampsDTO1).isNotEqualTo(stampsDTO2);
        stampsDTO1.setId(null);
        assertThat(stampsDTO1).isNotEqualTo(stampsDTO2);
    }
}
