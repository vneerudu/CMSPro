package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AccountStatusesMapperTest {

    private AccountStatusesMapper accountStatusesMapper;

    @BeforeEach
    public void setUp() {
        accountStatusesMapper = new AccountStatusesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(accountStatusesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(accountStatusesMapper.fromId(null)).isNull();
    }
}
