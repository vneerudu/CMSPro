package com.cmspro.basemicroservice.service.mapper;

import com.cmspro.basemicroservice.domain.*;
import com.cmspro.basemicroservice.service.dto.ProjectsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Projects} and its DTO {@link ProjectsDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProjectStatusMapper.class})
public interface ProjectsMapper extends EntityMapper<ProjectsDTO, Projects> {

    @Mapping(source = "projectStatusRel.id", target = "projectStatusRelId")
    @Mapping(source = "projectStatusRel.description", target = "projectStatusRelDescription")
    ProjectsDTO toDto(Projects projects);

    @Mapping(source = "projectStatusRelId", target = "projectStatusRel")
    Projects toEntity(ProjectsDTO projectsDTO);

    default Projects fromId(Long id) {
        if (id == null) {
            return null;
        }
        Projects projects = new Projects();
        projects.setId(id);
        return projects;
    }
}
