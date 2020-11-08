package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.RFIStatusesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RFIStatuses} and its DTO {@link RFIStatusesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RFIStatusesMapper extends EntityMapper<RFIStatusesDTO, RFIStatuses> {


    @Mapping(target = "rfi", ignore = true)
    RFIStatuses toEntity(RFIStatusesDTO rFIStatusesDTO);

    default RFIStatuses fromId(String id) {
        if (id == null) {
            return null;
        }
        RFIStatuses rFIStatuses = new RFIStatuses();
        rFIStatuses.setId(id);
        return rFIStatuses;
    }
}
