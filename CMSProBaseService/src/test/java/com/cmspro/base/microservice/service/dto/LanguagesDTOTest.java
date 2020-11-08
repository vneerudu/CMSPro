package com.cmspro.base.microservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class LanguagesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LanguagesDTO.class);
        LanguagesDTO languagesDTO1 = new LanguagesDTO();
        languagesDTO1.setId("id1");
        LanguagesDTO languagesDTO2 = new LanguagesDTO();
        assertThat(languagesDTO1).isNotEqualTo(languagesDTO2);
        languagesDTO2.setId(languagesDTO1.getId());
        assertThat(languagesDTO1).isEqualTo(languagesDTO2);
        languagesDTO2.setId("id2");
        assertThat(languagesDTO1).isNotEqualTo(languagesDTO2);
        languagesDTO1.setId(null);
        assertThat(languagesDTO1).isNotEqualTo(languagesDTO2);
    }
}
