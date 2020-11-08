package com.cmspro.base.microservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class LogosTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Logos.class);
        Logos logos1 = new Logos();
        logos1.setId("id1");
        Logos logos2 = new Logos();
        logos2.setId(logos1.getId());
        assertThat(logos1).isEqualTo(logos2);
        logos2.setId("id2");
        assertThat(logos1).isNotEqualTo(logos2);
        logos1.setId(null);
        assertThat(logos1).isNotEqualTo(logos2);
    }
}
