package com.cmspro.base.microservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class SheetHistoryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SheetHistoryDTO.class);
        SheetHistoryDTO sheetHistoryDTO1 = new SheetHistoryDTO();
        sheetHistoryDTO1.setId("id1");
        SheetHistoryDTO sheetHistoryDTO2 = new SheetHistoryDTO();
        assertThat(sheetHistoryDTO1).isNotEqualTo(sheetHistoryDTO2);
        sheetHistoryDTO2.setId(sheetHistoryDTO1.getId());
        assertThat(sheetHistoryDTO1).isEqualTo(sheetHistoryDTO2);
        sheetHistoryDTO2.setId("id2");
        assertThat(sheetHistoryDTO1).isNotEqualTo(sheetHistoryDTO2);
        sheetHistoryDTO1.setId(null);
        assertThat(sheetHistoryDTO1).isNotEqualTo(sheetHistoryDTO2);
    }
}
