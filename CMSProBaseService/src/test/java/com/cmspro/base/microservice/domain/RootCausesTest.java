package com.cmspro.base.microservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class RootCausesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RootCauses.class);
        RootCauses rootCauses1 = new RootCauses();
        rootCauses1.setId("id1");
        RootCauses rootCauses2 = new RootCauses();
        rootCauses2.setId(rootCauses1.getId());
        assertThat(rootCauses1).isEqualTo(rootCauses2);
        rootCauses2.setId("id2");
        assertThat(rootCauses1).isNotEqualTo(rootCauses2);
        rootCauses1.setId(null);
        assertThat(rootCauses1).isNotEqualTo(rootCauses2);
    }
}
