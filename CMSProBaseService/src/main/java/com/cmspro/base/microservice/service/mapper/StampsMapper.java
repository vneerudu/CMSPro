package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.StampsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Stamps} and its DTO {@link StampsDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProjectsMapper.class})
public interface StampsMapper extends EntityMapper<StampsDTO, Stamps> {

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "project.name", target = "projectName")
    StampsDTO toDto(Stamps stamps);

    @Mapping(target = "task", ignore = true)
    @Mapping(source = "projectId", target = "project")
    Stamps toEntity(StampsDTO stampsDTO);

    default Stamps fromId(String id) {
        if (id == null) {
            return null;
        }
        Stamps stamps = new Stamps();
        stamps.setId(id);
        return stamps;
    }
}
