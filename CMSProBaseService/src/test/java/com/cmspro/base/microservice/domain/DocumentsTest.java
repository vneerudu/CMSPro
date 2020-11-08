package com.cmspro.base.microservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class DocumentsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Documents.class);
        Documents documents1 = new Documents();
        documents1.setId("id1");
        Documents documents2 = new Documents();
        documents2.setId(documents1.getId());
        assertThat(documents1).isEqualTo(documents2);
        documents2.setId("id2");
        assertThat(documents1).isNotEqualTo(documents2);
        documents1.setId(null);
        assertThat(documents1).isNotEqualTo(documents2);
    }
}
