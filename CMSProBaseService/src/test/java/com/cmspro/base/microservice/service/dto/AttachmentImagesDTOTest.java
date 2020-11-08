package com.cmspro.base.microservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class AttachmentImagesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttachmentImagesDTO.class);
        AttachmentImagesDTO attachmentImagesDTO1 = new AttachmentImagesDTO();
        attachmentImagesDTO1.setId("id1");
        AttachmentImagesDTO attachmentImagesDTO2 = new AttachmentImagesDTO();
        assertThat(attachmentImagesDTO1).isNotEqualTo(attachmentImagesDTO2);
        attachmentImagesDTO2.setId(attachmentImagesDTO1.getId());
        assertThat(attachmentImagesDTO1).isEqualTo(attachmentImagesDTO2);
        attachmentImagesDTO2.setId("id2");
        assertThat(attachmentImagesDTO1).isNotEqualTo(attachmentImagesDTO2);
        attachmentImagesDTO1.setId(null);
        assertThat(attachmentImagesDTO1).isNotEqualTo(attachmentImagesDTO2);
    }
}
