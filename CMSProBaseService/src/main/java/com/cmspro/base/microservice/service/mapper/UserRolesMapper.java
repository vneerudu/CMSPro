package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.UserRolesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserRoles} and its DTO {@link UserRolesDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserGroupsMapper.class})
public interface UserRolesMapper extends EntityMapper<UserRolesDTO, UserRoles> {

    @Mapping(source = "userGroup.id", target = "userGroupId")
    @Mapping(source = "userGroup.description", target = "userGroupDescription")
    UserRolesDTO toDto(UserRoles userRoles);

    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "removePermissions", ignore = true)
    @Mapping(target = "menuItems", ignore = true)
    @Mapping(target = "removeMenuItems", ignore = true)
    @Mapping(source = "userGroupId", target = "userGroup")
    UserRoles toEntity(UserRolesDTO userRolesDTO);

    default UserRoles fromId(String id) {
        if (id == null) {
            return null;
        }
        UserRoles userRoles = new UserRoles();
        userRoles.setId(id);
        return userRoles;
    }
}
