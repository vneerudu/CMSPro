package com.cmspro.base.microservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class DocumentsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentsDTO.class);
        DocumentsDTO documentsDTO1 = new DocumentsDTO();
        documentsDTO1.setId("id1");
        DocumentsDTO documentsDTO2 = new DocumentsDTO();
        assertThat(documentsDTO1).isNotEqualTo(documentsDTO2);
        documentsDTO2.setId(documentsDTO1.getId());
        assertThat(documentsDTO1).isEqualTo(documentsDTO2);
        documentsDTO2.setId("id2");
        assertThat(documentsDTO1).isNotEqualTo(documentsDTO2);
        documentsDTO1.setId(null);
        assertThat(documentsDTO1).isNotEqualTo(documentsDTO2);
    }
}
