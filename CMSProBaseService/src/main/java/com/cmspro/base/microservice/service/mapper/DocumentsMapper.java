package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.DocumentsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Documents} and its DTO {@link DocumentsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DocumentsMapper extends EntityMapper<DocumentsDTO, Documents> {


    @Mapping(target = "sheets", ignore = true)
    Documents toEntity(DocumentsDTO documentsDTO);

    default Documents fromId(String id) {
        if (id == null) {
            return null;
        }
        Documents documents = new Documents();
        documents.setId(id);
        return documents;
    }
}
