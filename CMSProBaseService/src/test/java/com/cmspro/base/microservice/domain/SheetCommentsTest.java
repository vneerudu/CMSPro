package com.cmspro.base.microservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class SheetCommentsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SheetComments.class);
        SheetComments sheetComments1 = new SheetComments();
        sheetComments1.setId("id1");
        SheetComments sheetComments2 = new SheetComments();
        sheetComments2.setId(sheetComments1.getId());
        assertThat(sheetComments1).isEqualTo(sheetComments2);
        sheetComments2.setId("id2");
        assertThat(sheetComments1).isNotEqualTo(sheetComments2);
        sheetComments1.setId(null);
        assertThat(sheetComments1).isNotEqualTo(sheetComments2);
    }
}
