package com.cmspro.base.microservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class MenuItemsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MenuItemsDTO.class);
        MenuItemsDTO menuItemsDTO1 = new MenuItemsDTO();
        menuItemsDTO1.setId("id1");
        MenuItemsDTO menuItemsDTO2 = new MenuItemsDTO();
        assertThat(menuItemsDTO1).isNotEqualTo(menuItemsDTO2);
        menuItemsDTO2.setId(menuItemsDTO1.getId());
        assertThat(menuItemsDTO1).isEqualTo(menuItemsDTO2);
        menuItemsDTO2.setId("id2");
        assertThat(menuItemsDTO1).isNotEqualTo(menuItemsDTO2);
        menuItemsDTO1.setId(null);
        assertThat(menuItemsDTO1).isNotEqualTo(menuItemsDTO2);
    }
}
