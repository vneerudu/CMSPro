package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AccountsMapperTest {

    private AccountsMapper accountsMapper;

    @BeforeEach
    public void setUp() {
        accountsMapper = new AccountsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(accountsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(accountsMapper.fromId(null)).isNull();
    }
}
