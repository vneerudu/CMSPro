package com.cmspro.base.microservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class StatesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatesDTO.class);
        StatesDTO statesDTO1 = new StatesDTO();
        statesDTO1.setId("id1");
        StatesDTO statesDTO2 = new StatesDTO();
        assertThat(statesDTO1).isNotEqualTo(statesDTO2);
        statesDTO2.setId(statesDTO1.getId());
        assertThat(statesDTO1).isEqualTo(statesDTO2);
        statesDTO2.setId("id2");
        assertThat(statesDTO1).isNotEqualTo(statesDTO2);
        statesDTO1.setId(null);
        assertThat(statesDTO1).isNotEqualTo(statesDTO2);
    }
}
