package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.SheetsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sheets} and its DTO {@link SheetsDTO}.
 */
@Mapper(componentModel = "spring", uses = {DocumentsMapper.class, ProjectsMapper.class})
public interface SheetsMapper extends EntityMapper<SheetsDTO, Sheets> {

    @Mapping(source = "documents.id", target = "documentsId")
    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "project.name", target = "projectName")
    SheetsDTO toDto(Sheets sheets);

    @Mapping(source = "documentsId", target = "documents")
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "removeTags", ignore = true)
    @Mapping(target = "histories", ignore = true)
    @Mapping(target = "removeHistory", ignore = true)
    @Mapping(target = "attachments", ignore = true)
    @Mapping(target = "removeAttachment", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "removeComment", ignore = true)
    @Mapping(target = "task", ignore = true)
    @Mapping(source = "projectId", target = "project")
    Sheets toEntity(SheetsDTO sheetsDTO);

    default Sheets fromId(String id) {
        if (id == null) {
            return null;
        }
        Sheets sheets = new Sheets();
        sheets.setId(id);
        return sheets;
    }
}
