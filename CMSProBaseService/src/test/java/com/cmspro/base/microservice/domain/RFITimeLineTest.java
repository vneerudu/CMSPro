package com.cmspro.base.microservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class RFITimeLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RFITimeLine.class);
        RFITimeLine rFITimeLine1 = new RFITimeLine();
        rFITimeLine1.setId("id1");
        RFITimeLine rFITimeLine2 = new RFITimeLine();
        rFITimeLine2.setId(rFITimeLine1.getId());
        assertThat(rFITimeLine1).isEqualTo(rFITimeLine2);
        rFITimeLine2.setId("id2");
        assertThat(rFITimeLine1).isNotEqualTo(rFITimeLine2);
        rFITimeLine1.setId(null);
        assertThat(rFITimeLine1).isNotEqualTo(rFITimeLine2);
    }
}
