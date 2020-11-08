package com.cmspro.base.microservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class SheetTagsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SheetTags.class);
        SheetTags sheetTags1 = new SheetTags();
        sheetTags1.setId("id1");
        SheetTags sheetTags2 = new SheetTags();
        sheetTags2.setId(sheetTags1.getId());
        assertThat(sheetTags1).isEqualTo(sheetTags2);
        sheetTags2.setId("id2");
        assertThat(sheetTags1).isNotEqualTo(sheetTags2);
        sheetTags1.setId(null);
        assertThat(sheetTags1).isNotEqualTo(sheetTags2);
    }
}
