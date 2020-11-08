package com.cmspro.base.microservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class RFICommentsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RFIComments.class);
        RFIComments rFIComments1 = new RFIComments();
        rFIComments1.setId("id1");
        RFIComments rFIComments2 = new RFIComments();
        rFIComments2.setId(rFIComments1.getId());
        assertThat(rFIComments1).isEqualTo(rFIComments2);
        rFIComments2.setId("id2");
        assertThat(rFIComments1).isNotEqualTo(rFIComments2);
        rFIComments1.setId(null);
        assertThat(rFIComments1).isNotEqualTo(rFIComments2);
    }
}
