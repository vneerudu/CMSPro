package com.cmspro.base.microservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class SheetsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sheets.class);
        Sheets sheets1 = new Sheets();
        sheets1.setId("id1");
        Sheets sheets2 = new Sheets();
        sheets2.setId(sheets1.getId());
        assertThat(sheets1).isEqualTo(sheets2);
        sheets2.setId("id2");
        assertThat(sheets1).isNotEqualTo(sheets2);
        sheets1.setId(null);
        assertThat(sheets1).isNotEqualTo(sheets2);
    }
}
