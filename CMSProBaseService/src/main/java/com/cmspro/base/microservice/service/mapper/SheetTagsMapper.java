package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.SheetTagsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SheetTags} and its DTO {@link SheetTagsDTO}.
 */
@Mapper(componentModel = "spring", uses = {SheetsMapper.class})
public interface SheetTagsMapper extends EntityMapper<SheetTagsDTO, SheetTags> {

    @Mapping(source = "sheets.id", target = "sheetsId")
    SheetTagsDTO toDto(SheetTags sheetTags);

    @Mapping(source = "sheetsId", target = "sheets")
    SheetTags toEntity(SheetTagsDTO sheetTagsDTO);

    default SheetTags fromId(String id) {
        if (id == null) {
            return null;
        }
        SheetTags sheetTags = new SheetTags();
        sheetTags.setId(id);
        return sheetTags;
    }
}
