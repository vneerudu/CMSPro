package com.cmspro.base.microservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class AttachmentOthersDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttachmentOthersDTO.class);
        AttachmentOthersDTO attachmentOthersDTO1 = new AttachmentOthersDTO();
        attachmentOthersDTO1.setId("id1");
        AttachmentOthersDTO attachmentOthersDTO2 = new AttachmentOthersDTO();
        assertThat(attachmentOthersDTO1).isNotEqualTo(attachmentOthersDTO2);
        attachmentOthersDTO2.setId(attachmentOthersDTO1.getId());
        assertThat(attachmentOthersDTO1).isEqualTo(attachmentOthersDTO2);
        attachmentOthersDTO2.setId("id2");
        assertThat(attachmentOthersDTO1).isNotEqualTo(attachmentOthersDTO2);
        attachmentOthersDTO1.setId(null);
        assertThat(attachmentOthersDTO1).isNotEqualTo(attachmentOthersDTO2);
    }
}
