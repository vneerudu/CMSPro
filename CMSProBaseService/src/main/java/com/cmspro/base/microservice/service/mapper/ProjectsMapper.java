package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.ProjectsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Projects} and its DTO {@link ProjectsDTO}.
 */
@Mapper(componentModel = "spring", uses = {AccountsMapper.class})
public interface ProjectsMapper extends EntityMapper<ProjectsDTO, Projects> {

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "account.accountNumber", target = "accountAccountNumber")
    ProjectsDTO toDto(Projects projects);

    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "removeAddress", ignore = true)
    @Mapping(target = "sheets", ignore = true)
    @Mapping(target = "removeSheets", ignore = true)
    @Mapping(target = "teams", ignore = true)
    @Mapping(target = "removeTeams", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "removeTasks", ignore = true)
    @Mapping(target = "stamps", ignore = true)
    @Mapping(target = "removeStamps", ignore = true)
    @Mapping(target = "lists", ignore = true)
    @Mapping(target = "removeList", ignore = true)
    @Mapping(target = "rfis", ignore = true)
    @Mapping(target = "removeRfi", ignore = true)
    @Mapping(source = "accountId", target = "account")
    Projects toEntity(ProjectsDTO projectsDTO);

    default Projects fromId(String id) {
        if (id == null) {
            return null;
        }
        Projects projects = new Projects();
        projects.setId(id);
        return projects;
    }
}
