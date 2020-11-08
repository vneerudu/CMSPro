package com.cmspro.base.microservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class SheetCommentsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SheetCommentsDTO.class);
        SheetCommentsDTO sheetCommentsDTO1 = new SheetCommentsDTO();
        sheetCommentsDTO1.setId("id1");
        SheetCommentsDTO sheetCommentsDTO2 = new SheetCommentsDTO();
        assertThat(sheetCommentsDTO1).isNotEqualTo(sheetCommentsDTO2);
        sheetCommentsDTO2.setId(sheetCommentsDTO1.getId());
        assertThat(sheetCommentsDTO1).isEqualTo(sheetCommentsDTO2);
        sheetCommentsDTO2.setId("id2");
        assertThat(sheetCommentsDTO1).isNotEqualTo(sheetCommentsDTO2);
        sheetCommentsDTO1.setId(null);
        assertThat(sheetCommentsDTO1).isNotEqualTo(sheetCommentsDTO2);
    }
}
