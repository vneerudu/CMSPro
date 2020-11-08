package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserPermissionsMapperTest {

    private UserPermissionsMapper userPermissionsMapper;

    @BeforeEach
    public void setUp() {
        userPermissionsMapper = new UserPermissionsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(userPermissionsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userPermissionsMapper.fromId(null)).isNull();
    }
}
