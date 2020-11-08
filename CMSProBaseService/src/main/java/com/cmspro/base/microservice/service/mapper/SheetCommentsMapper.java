package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.SheetCommentsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SheetComments} and its DTO {@link SheetCommentsDTO}.
 */
@Mapper(componentModel = "spring", uses = {SheetsMapper.class})
public interface SheetCommentsMapper extends EntityMapper<SheetCommentsDTO, SheetComments> {

    @Mapping(source = "sheet.id", target = "sheetId")
    @Mapping(source = "sheet.number", target = "sheetNumber")
    SheetCommentsDTO toDto(SheetComments sheetComments);

    @Mapping(source = "sheetId", target = "sheet")
    SheetComments toEntity(SheetCommentsDTO sheetCommentsDTO);

    default SheetComments fromId(String id) {
        if (id == null) {
            return null;
        }
        SheetComments sheetComments = new SheetComments();
        sheetComments.setId(id);
        return sheetComments;
    }
}
