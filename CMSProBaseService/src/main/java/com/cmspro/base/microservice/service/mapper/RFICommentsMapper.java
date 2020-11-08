package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.RFICommentsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RFIComments} and its DTO {@link RFICommentsDTO}.
 */
@Mapper(componentModel = "spring", uses = {RFIMapper.class})
public interface RFICommentsMapper extends EntityMapper<RFICommentsDTO, RFIComments> {

    @Mapping(source = "rfi.id", target = "rfiId")
    @Mapping(source = "rfi.title", target = "rfiTitle")
    RFICommentsDTO toDto(RFIComments rFIComments);

    @Mapping(source = "rfiId", target = "rfi")
    RFIComments toEntity(RFICommentsDTO rFICommentsDTO);

    default RFIComments fromId(String id) {
        if (id == null) {
            return null;
        }
        RFIComments rFIComments = new RFIComments();
        rFIComments.setId(id);
        return rFIComments;
    }
}
