package com.cmspro.base.microservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class LogosDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LogosDTO.class);
        LogosDTO logosDTO1 = new LogosDTO();
        logosDTO1.setId("id1");
        LogosDTO logosDTO2 = new LogosDTO();
        assertThat(logosDTO1).isNotEqualTo(logosDTO2);
        logosDTO2.setId(logosDTO1.getId());
        assertThat(logosDTO1).isEqualTo(logosDTO2);
        logosDTO2.setId("id2");
        assertThat(logosDTO1).isNotEqualTo(logosDTO2);
        logosDTO1.setId(null);
        assertThat(logosDTO1).isNotEqualTo(logosDTO2);
    }
}
