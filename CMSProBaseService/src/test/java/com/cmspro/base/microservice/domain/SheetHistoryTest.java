package com.cmspro.base.microservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class SheetHistoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SheetHistory.class);
        SheetHistory sheetHistory1 = new SheetHistory();
        sheetHistory1.setId("id1");
        SheetHistory sheetHistory2 = new SheetHistory();
        sheetHistory2.setId(sheetHistory1.getId());
        assertThat(sheetHistory1).isEqualTo(sheetHistory2);
        sheetHistory2.setId("id2");
        assertThat(sheetHistory1).isNotEqualTo(sheetHistory2);
        sheetHistory1.setId(null);
        assertThat(sheetHistory1).isNotEqualTo(sheetHistory2);
    }
}
