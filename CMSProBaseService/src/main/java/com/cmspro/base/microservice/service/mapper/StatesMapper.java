package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.StatesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link States} and its DTO {@link StatesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StatesMapper extends EntityMapper<StatesDTO, States> {



    default States fromId(String id) {
        if (id == null) {
            return null;
        }
        States states = new States();
        states.setId(id);
        return states;
    }
}
