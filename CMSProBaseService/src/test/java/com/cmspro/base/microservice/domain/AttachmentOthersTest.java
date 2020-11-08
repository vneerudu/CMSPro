package com.cmspro.base.microservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class AttachmentOthersTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttachmentOthers.class);
        AttachmentOthers attachmentOthers1 = new AttachmentOthers();
        attachmentOthers1.setId("id1");
        AttachmentOthers attachmentOthers2 = new AttachmentOthers();
        attachmentOthers2.setId(attachmentOthers1.getId());
        assertThat(attachmentOthers1).isEqualTo(attachmentOthers2);
        attachmentOthers2.setId("id2");
        assertThat(attachmentOthers1).isNotEqualTo(attachmentOthers2);
        attachmentOthers1.setId(null);
        assertThat(attachmentOthers1).isNotEqualTo(attachmentOthers2);
    }
}
