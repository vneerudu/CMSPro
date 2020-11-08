package com.cmspro.base.microservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class MenuItemsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MenuItems.class);
        MenuItems menuItems1 = new MenuItems();
        menuItems1.setId("id1");
        MenuItems menuItems2 = new MenuItems();
        menuItems2.setId(menuItems1.getId());
        assertThat(menuItems1).isEqualTo(menuItems2);
        menuItems2.setId("id2");
        assertThat(menuItems1).isNotEqualTo(menuItems2);
        menuItems1.setId(null);
        assertThat(menuItems1).isNotEqualTo(menuItems2);
    }
}
