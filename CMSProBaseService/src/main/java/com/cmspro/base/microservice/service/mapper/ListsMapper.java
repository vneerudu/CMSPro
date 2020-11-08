package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.ListsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Lists} and its DTO {@link ListsDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProjectsMapper.class})
public interface ListsMapper extends EntityMapper<ListsDTO, Lists> {

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "project.name", target = "projectName")
    ListsDTO toDto(Lists lists);

    @Mapping(target = "task", ignore = true)
    @Mapping(source = "projectId", target = "project")
    Lists toEntity(ListsDTO listsDTO);

    default Lists fromId(String id) {
        if (id == null) {
            return null;
        }
        Lists lists = new Lists();
        lists.setId(id);
        return lists;
    }
}
