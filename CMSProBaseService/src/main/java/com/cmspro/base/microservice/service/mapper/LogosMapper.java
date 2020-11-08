package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.LogosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Logos} and its DTO {@link LogosDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LogosMapper extends EntityMapper<LogosDTO, Logos> {



    default Logos fromId(String id) {
        if (id == null) {
            return null;
        }
        Logos logos = new Logos();
        logos.setId(id);
        return logos;
    }
}
