package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserRolesMapperTest {

    private UserRolesMapper userRolesMapper;

    @BeforeEach
    public void setUp() {
        userRolesMapper = new UserRolesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(userRolesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userRolesMapper.fromId(null)).isNull();
    }
}
