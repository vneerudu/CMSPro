package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.AttachmentsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Attachments} and its DTO {@link AttachmentsDTO}.
 */
@Mapper(componentModel = "spring", uses = {AttachmentImagesMapper.class, AttachmentOthersMapper.class, SheetsMapper.class})
public interface AttachmentsMapper extends EntityMapper<AttachmentsDTO, Attachments> {

    @Mapping(source = "image.id", target = "imageId")
    @Mapping(source = "image.fileName", target = "imageFileName")
    @Mapping(source = "pdfattachment.id", target = "pdfattachmentId")
    @Mapping(source = "pdfattachment.fileName", target = "pdfattachmentFileName")
    @Mapping(source = "sheet.id", target = "sheetId")
    @Mapping(source = "sheet.number", target = "sheetNumber")
    AttachmentsDTO toDto(Attachments attachments);

    @Mapping(source = "imageId", target = "image")
    @Mapping(source = "pdfattachmentId", target = "pdfattachment")
    @Mapping(source = "sheetId", target = "sheet")
    Attachments toEntity(AttachmentsDTO attachmentsDTO);

    default Attachments fromId(String id) {
        if (id == null) {
            return null;
        }
        Attachments attachments = new Attachments();
        attachments.setId(id);
        return attachments;
    }
}
