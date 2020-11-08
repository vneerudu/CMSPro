package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.SheetHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SheetHistory} and its DTO {@link SheetHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {SheetsMapper.class})
public interface SheetHistoryMapper extends EntityMapper<SheetHistoryDTO, SheetHistory> {

    @Mapping(source = "sheet.id", target = "sheetId")
    @Mapping(source = "sheet.number", target = "sheetNumber")
    SheetHistoryDTO toDto(SheetHistory sheetHistory);

    @Mapping(source = "sheetId", target = "sheet")
    SheetHistory toEntity(SheetHistoryDTO sheetHistoryDTO);

    default SheetHistory fromId(String id) {
        if (id == null) {
            return null;
        }
        SheetHistory sheetHistory = new SheetHistory();
        sheetHistory.setId(id);
        return sheetHistory;
    }
}
