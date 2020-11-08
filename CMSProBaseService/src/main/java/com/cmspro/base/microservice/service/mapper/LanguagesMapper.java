package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.LanguagesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Languages} and its DTO {@link LanguagesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LanguagesMapper extends EntityMapper<LanguagesDTO, Languages> {



    default Languages fromId(String id) {
        if (id == null) {
            return null;
        }
        Languages languages = new Languages();
        languages.setId(id);
        return languages;
    }
}
