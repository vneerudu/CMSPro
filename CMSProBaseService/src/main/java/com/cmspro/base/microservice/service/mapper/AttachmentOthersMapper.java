package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.AttachmentOthersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AttachmentOthers} and its DTO {@link AttachmentOthersDTO}.
 */
@Mapper(componentModel = "spring", uses = {RFIMapper.class})
public interface AttachmentOthersMapper extends EntityMapper<AttachmentOthersDTO, AttachmentOthers> {

    @Mapping(source = "rfi.id", target = "rfiId")
    @Mapping(source = "rfi.title", target = "rfiTitle")
    AttachmentOthersDTO toDto(AttachmentOthers attachmentOthers);

    @Mapping(target = "attachment", ignore = true)
    @Mapping(source = "rfiId", target = "rfi")
    AttachmentOthers toEntity(AttachmentOthersDTO attachmentOthersDTO);

    default AttachmentOthers fromId(String id) {
        if (id == null) {
            return null;
        }
        AttachmentOthers attachmentOthers = new AttachmentOthers();
        attachmentOthers.setId(id);
        return attachmentOthers;
    }
}
