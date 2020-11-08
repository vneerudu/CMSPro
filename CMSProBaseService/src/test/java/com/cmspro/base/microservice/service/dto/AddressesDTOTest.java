package com.cmspro.base.microservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cmspro.base.microservice.web.rest.TestUtil;

public class AddressesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddressesDTO.class);
        AddressesDTO addressesDTO1 = new AddressesDTO();
        addressesDTO1.setId("id1");
        AddressesDTO addressesDTO2 = new AddressesDTO();
        assertThat(addressesDTO1).isNotEqualTo(addressesDTO2);
        addressesDTO2.setId(addressesDTO1.getId());
        assertThat(addressesDTO1).isEqualTo(addressesDTO2);
        addressesDTO2.setId("id2");
        assertThat(addressesDTO1).isNotEqualTo(addressesDTO2);
        addressesDTO1.setId(null);
        assertThat(addressesDTO1).isNotEqualTo(addressesDTO2);
    }
}
