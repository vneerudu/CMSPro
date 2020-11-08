package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.AttachmentImagesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AttachmentImages} and its DTO {@link AttachmentImagesDTO}.
 */
@Mapper(componentModel = "spring", uses = {RFIMapper.class})
public interface AttachmentImagesMapper extends EntityMapper<AttachmentImagesDTO, AttachmentImages> {

    @Mapping(source = "rfi.id", target = "rfiId")
    @Mapping(source = "rfi.title", target = "rfiTitle")
    AttachmentImagesDTO toDto(AttachmentImages attachmentImages);

    @Mapping(target = "attachment", ignore = true)
    @Mapping(source = "rfiId", target = "rfi")
    AttachmentImages toEntity(AttachmentImagesDTO attachmentImagesDTO);

    default AttachmentImages fromId(String id) {
        if (id == null) {
            return null;
        }
        AttachmentImages attachmentImages = new AttachmentImages();
        attachmentImages.setId(id);
        return attachmentImages;
    }
}
