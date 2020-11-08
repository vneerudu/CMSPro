package com.cmspro.base.microservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class LanguagesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Languages.class);
        Languages languages1 = new Languages();
        languages1.setId("id1");
        Languages languages2 = new Languages();
        languages2.setId(languages1.getId());
        assertThat(languages1).isEqualTo(languages2);
        languages2.setId("id2");
        assertThat(languages1).isNotEqualTo(languages2);
        languages1.setId(null);
        assertThat(languages1).isNotEqualTo(languages2);
    }
}
