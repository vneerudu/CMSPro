package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.UserPermissionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserPermissions} and its DTO {@link UserPermissionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserRolesMapper.class})
public interface UserPermissionsMapper extends EntityMapper<UserPermissionsDTO, UserPermissions> {

    @Mapping(source = "userRoles.id", target = "userRolesId")
    @Mapping(source = "userRoles.description", target = "userRolesDescription")
    UserPermissionsDTO toDto(UserPermissions userPermissions);

    @Mapping(source = "userRolesId", target = "userRoles")
    UserPermissions toEntity(UserPermissionsDTO userPermissionsDTO);

    default UserPermissions fromId(String id) {
        if (id == null) {
            return null;
        }
        UserPermissions userPermissions = new UserPermissions();
        userPermissions.setId(id);
        return userPermissions;
    }
}
