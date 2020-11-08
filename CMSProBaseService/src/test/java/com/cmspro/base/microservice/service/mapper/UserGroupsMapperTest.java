package com.cmspro.base.microservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserGroupsMapperTest {

    private UserGroupsMapper userGroupsMapper;

    @BeforeEach
    public void setUp() {
        userGroupsMapper = new UserGroupsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(userGroupsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userGroupsMapper.fromId(null)).isNull();
    }
}
