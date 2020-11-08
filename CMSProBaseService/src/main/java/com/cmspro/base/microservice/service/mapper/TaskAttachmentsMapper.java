package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.TaskAttachmentsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaskAttachments} and its DTO {@link TaskAttachmentsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TaskAttachmentsMapper extends EntityMapper<TaskAttachmentsDTO, TaskAttachments> {



    default TaskAttachments fromId(String id) {
        if (id == null) {
            return null;
        }
        TaskAttachments taskAttachments = new TaskAttachments();
        taskAttachments.setId(id);
        return taskAttachments;
    }
}
