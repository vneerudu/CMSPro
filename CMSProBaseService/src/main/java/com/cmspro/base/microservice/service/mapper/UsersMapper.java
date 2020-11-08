package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.UsersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Users} and its DTO {@link UsersDTO}.
 */
@Mapper(componentModel = "spring", uses = {AccountsMapper.class, ProjectTeamsMapper.class, TasksMapper.class})
public interface UsersMapper extends EntityMapper<UsersDTO, Users> {

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "account.accountNumber", target = "accountAccountNumber")
    @Mapping(source = "team.id", target = "teamId")
    @Mapping(source = "team.name", target = "teamName")
    @Mapping(source = "taskAssigned.id", target = "taskAssignedId")
    @Mapping(source = "taskAssigned.title", target = "taskAssignedTitle")
    @Mapping(source = "taskToMonitor.id", target = "taskToMonitorId")
    @Mapping(source = "taskToMonitor.title", target = "taskToMonitorTitle")
    UsersDTO toDto(Users users);

    @Mapping(target = "userGroups", ignore = true)
    @Mapping(target = "removeUserGroup", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "removeAddress", ignore = true)
    @Mapping(source = "accountId", target = "account")
    @Mapping(source = "teamId", target = "team")
    @Mapping(source = "taskAssignedId", target = "taskAssigned")
    @Mapping(source = "taskToMonitorId", target = "taskToMonitor")
    Users toEntity(UsersDTO usersDTO);

    default Users fromId(String id) {
        if (id == null) {
            return null;
        }
        Users users = new Users();
        users.setId(id);
        return users;
    }
}
