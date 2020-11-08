package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UsersMapperTest {

    private UsersMapper usersMapper;

    @BeforeEach
    public void setUp() {
        usersMapper = new UsersMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(usersMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(usersMapper.fromId(null)).isNull();
    }
}
