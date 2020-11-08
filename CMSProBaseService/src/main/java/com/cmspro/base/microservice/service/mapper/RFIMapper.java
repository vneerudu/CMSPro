package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.RFIDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RFI} and its DTO {@link RFIDTO}.
 */
@Mapper(componentModel = "spring", uses = {RFIStatusesMapper.class, ProjectsMapper.class})
public interface RFIMapper extends EntityMapper<RFIDTO, RFI> {

    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "status.description", target = "statusDescription")
    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "project.name", target = "projectName")
    RFIDTO toDto(RFI rFI);

    @Mapping(source = "statusId", target = "status")
    @Mapping(target = "taskImages", ignore = true)
    @Mapping(target = "removeTaskImages", ignore = true)
    @Mapping(target = "taskAttachments", ignore = true)
    @Mapping(target = "removeTaskAttachment", ignore = true)
    @Mapping(target = "sheetAttachments", ignore = true)
    @Mapping(target = "removeSheetAttachment", ignore = true)
    @Mapping(target = "sheetImages", ignore = true)
    @Mapping(target = "removeSheetImages", ignore = true)
    @Mapping(target = "rfiComments", ignore = true)
    @Mapping(target = "removeRfiComments", ignore = true)
    @Mapping(target = "timeLines", ignore = true)
    @Mapping(target = "removeTimeLine", ignore = true)
    @Mapping(source = "projectId", target = "project")
    RFI toEntity(RFIDTO rFIDTO);

    default RFI fromId(String id) {
        if (id == null) {
            return null;
        }
        RFI rFI = new RFI();
        rFI.setId(id);
        return rFI;
    }
}
