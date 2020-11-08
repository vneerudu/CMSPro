package com.cmspro.base.microservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class SheetsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SheetsDTO.class);
        SheetsDTO sheetsDTO1 = new SheetsDTO();
        sheetsDTO1.setId("id1");
        SheetsDTO sheetsDTO2 = new SheetsDTO();
        assertThat(sheetsDTO1).isNotEqualTo(sheetsDTO2);
        sheetsDTO2.setId(sheetsDTO1.getId());
        assertThat(sheetsDTO1).isEqualTo(sheetsDTO2);
        sheetsDTO2.setId("id2");
        assertThat(sheetsDTO1).isNotEqualTo(sheetsDTO2);
        sheetsDTO1.setId(null);
        assertThat(sheetsDTO1).isNotEqualTo(sheetsDTO2);
    }
}
