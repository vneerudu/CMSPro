package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.RFITimeLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RFITimeLine} and its DTO {@link RFITimeLineDTO}.
 */
@Mapper(componentModel = "spring", uses = {RFIMapper.class})
public interface RFITimeLineMapper extends EntityMapper<RFITimeLineDTO, RFITimeLine> {

    @Mapping(source = "rfi.id", target = "rfiId")
    @Mapping(source = "rfi.title", target = "rfiTitle")
    RFITimeLineDTO toDto(RFITimeLine rFITimeLine);

    @Mapping(source = "rfiId", target = "rfi")
    RFITimeLine toEntity(RFITimeLineDTO rFITimeLineDTO);

    default RFITimeLine fromId(String id) {
        if (id == null) {
            return null;
        }
        RFITimeLine rFITimeLine = new RFITimeLine();
        rFITimeLine.setId(id);
        return rFITimeLine;
    }
}
