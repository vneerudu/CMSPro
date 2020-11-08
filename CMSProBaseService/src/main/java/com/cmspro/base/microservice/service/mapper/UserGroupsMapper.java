package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.UserGroupsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserGroups} and its DTO {@link UserGroupsDTO}.
 */
@Mapper(componentModel = "spring", uses = {AccountsMapper.class, UsersMapper.class})
public interface UserGroupsMapper extends EntityMapper<UserGroupsDTO, UserGroups> {

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "account.accountNumber", target = "accountAccountNumber")
    @Mapping(source = "users.id", target = "usersId")
    @Mapping(source = "users.fullName", target = "usersFullName")
    UserGroupsDTO toDto(UserGroups userGroups);

    @Mapping(target = "userRoles", ignore = true)
    @Mapping(target = "removeUserRoles", ignore = true)
    @Mapping(source = "accountId", target = "account")
    @Mapping(source = "usersId", target = "users")
    UserGroups toEntity(UserGroupsDTO userGroupsDTO);

    default UserGroups fromId(String id) {
        if (id == null) {
            return null;
        }
        UserGroups userGroups = new UserGroups();
        userGroups.setId(id);
        return userGroups;
    }
}
