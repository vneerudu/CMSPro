package com.cmspro.base.microservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class ListsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListsDTO.class);
        ListsDTO listsDTO1 = new ListsDTO();
        listsDTO1.setId("id1");
        ListsDTO listsDTO2 = new ListsDTO();
        assertThat(listsDTO1).isNotEqualTo(listsDTO2);
        listsDTO2.setId(listsDTO1.getId());
        assertThat(listsDTO1).isEqualTo(listsDTO2);
        listsDTO2.setId("id2");
        assertThat(listsDTO1).isNotEqualTo(listsDTO2);
        listsDTO1.setId(null);
        assertThat(listsDTO1).isNotEqualTo(listsDTO2);
    }
}
