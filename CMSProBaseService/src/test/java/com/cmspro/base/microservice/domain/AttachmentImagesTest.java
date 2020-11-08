package com.cmspro.base.microservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class AttachmentImagesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttachmentImages.class);
        AttachmentImages attachmentImages1 = new AttachmentImages();
        attachmentImages1.setId("id1");
        AttachmentImages attachmentImages2 = new AttachmentImages();
        attachmentImages2.setId(attachmentImages1.getId());
        assertThat(attachmentImages1).isEqualTo(attachmentImages2);
        attachmentImages2.setId("id2");
        assertThat(attachmentImages1).isNotEqualTo(attachmentImages2);
        attachmentImages1.setId(null);
        assertThat(attachmentImages1).isNotEqualTo(attachmentImages2);
    }
}
