package com.cmspro.base.microservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class SheetTagsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SheetTagsDTO.class);
        SheetTagsDTO sheetTagsDTO1 = new SheetTagsDTO();
        sheetTagsDTO1.setId("id1");
        SheetTagsDTO sheetTagsDTO2 = new SheetTagsDTO();
        assertThat(sheetTagsDTO1).isNotEqualTo(sheetTagsDTO2);
        sheetTagsDTO2.setId(sheetTagsDTO1.getId());
        assertThat(sheetTagsDTO1).isEqualTo(sheetTagsDTO2);
        sheetTagsDTO2.setId("id2");
        assertThat(sheetTagsDTO1).isNotEqualTo(sheetTagsDTO2);
        sheetTagsDTO1.setId(null);
        assertThat(sheetTagsDTO1).isNotEqualTo(sheetTagsDTO2);
    }
}
