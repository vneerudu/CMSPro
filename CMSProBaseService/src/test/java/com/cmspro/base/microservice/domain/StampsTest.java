package com.cmspro.base.microservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class StampsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Stamps.class);
        Stamps stamps1 = new Stamps();
        stamps1.setId("id1");
        Stamps stamps2 = new Stamps();
        stamps2.setId(stamps1.getId());
        assertThat(stamps1).isEqualTo(stamps2);
        stamps2.setId("id2");
        assertThat(stamps1).isNotEqualTo(stamps2);
        stamps1.setId(null);
        assertThat(stamps1).isNotEqualTo(stamps2);
    }
}
