package com.cmspro.base.microservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class StatesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(States.class);
        States states1 = new States();
        states1.setId("id1");
        States states2 = new States();
        states2.setId(states1.getId());
        assertThat(states1).isEqualTo(states2);
        states2.setId("id2");
        assertThat(states1).isNotEqualTo(states2);
        states1.setId(null);
        assertThat(states1).isNotEqualTo(states2);
    }
}
