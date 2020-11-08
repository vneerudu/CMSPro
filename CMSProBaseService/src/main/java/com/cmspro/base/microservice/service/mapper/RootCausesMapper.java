package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.RootCausesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RootCauses} and its DTO {@link RootCausesDTO}.
 */
@Mapper(componentModel = "spring", uses = {RootCauseGroupsMapper.class})
public interface RootCausesMapper extends EntityMapper<RootCausesDTO, RootCauses> {

    @Mapping(source = "groups.id", target = "groupsId")
    @Mapping(source = "groups.code", target = "groupsCode")
    RootCausesDTO toDto(RootCauses rootCauses);

    @Mapping(target = "task", ignore = true)
    @Mapping(source = "groupsId", target = "groups")
    RootCauses toEntity(RootCausesDTO rootCausesDTO);

    default RootCauses fromId(String id) {
        if (id == null) {
            return null;
        }
        RootCauses rootCauses = new RootCauses();
        rootCauses.setId(id);
        return rootCauses;
    }
}
