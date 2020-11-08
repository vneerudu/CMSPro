package com.cmspro.base.microservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class ListsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lists.class);
        Lists lists1 = new Lists();
        lists1.setId("id1");
        Lists lists2 = new Lists();
        lists2.setId(lists1.getId());
        assertThat(lists1).isEqualTo(lists2);
        lists2.setId("id2");
        assertThat(lists1).isNotEqualTo(lists2);
        lists1.setId(null);
        assertThat(lists1).isNotEqualTo(lists2);
    }
}
