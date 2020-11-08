package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.RootCauseGroupsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RootCauseGroups} and its DTO {@link RootCauseGroupsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RootCauseGroupsMapper extends EntityMapper<RootCauseGroupsDTO, RootCauseGroups> {


    @Mapping(target = "rootCauses", ignore = true)
    @Mapping(target = "removeRootCause", ignore = true)
    RootCauseGroups toEntity(RootCauseGroupsDTO rootCauseGroupsDTO);

    default RootCauseGroups fromId(String id) {
        if (id == null) {
            return null;
        }
        RootCauseGroups rootCauseGroups = new RootCauseGroups();
        rootCauseGroups.setId(id);
        return rootCauseGroups;
    }
}
