package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.ProjectTeamsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProjectTeams} and its DTO {@link ProjectTeamsDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProjectsMapper.class})
public interface ProjectTeamsMapper extends EntityMapper<ProjectTeamsDTO, ProjectTeams> {

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "project.name", target = "projectName")
    ProjectTeamsDTO toDto(ProjectTeams projectTeams);

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "removeUsers", ignore = true)
    @Mapping(source = "projectId", target = "project")
    ProjectTeams toEntity(ProjectTeamsDTO projectTeamsDTO);

    default ProjectTeams fromId(String id) {
        if (id == null) {
            return null;
        }
        ProjectTeams projectTeams = new ProjectTeams();
        projectTeams.setId(id);
        return projectTeams;
    }
}
