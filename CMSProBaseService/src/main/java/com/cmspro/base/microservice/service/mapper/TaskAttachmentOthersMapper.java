package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.TaskAttachmentOthersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaskAttachmentOthers} and its DTO {@link TaskAttachmentOthersDTO}.
 */
@Mapper(componentModel = "spring", uses = {TasksMapper.class, RFIMapper.class})
public interface TaskAttachmentOthersMapper extends EntityMapper<TaskAttachmentOthersDTO, TaskAttachmentOthers> {

    @Mapping(source = "task.id", target = "taskId")
    @Mapping(source = "task.title", target = "taskTitle")
    @Mapping(source = "rfi.id", target = "rfiId")
    @Mapping(source = "rfi.title", target = "rfiTitle")
    TaskAttachmentOthersDTO toDto(TaskAttachmentOthers taskAttachmentOthers);

    @Mapping(source = "taskId", target = "task")
    @Mapping(source = "rfiId", target = "rfi")
    TaskAttachmentOthers toEntity(TaskAttachmentOthersDTO taskAttachmentOthersDTO);

    default TaskAttachmentOthers fromId(String id) {
        if (id == null) {
            return null;
        }
        TaskAttachmentOthers taskAttachmentOthers = new TaskAttachmentOthers();
        taskAttachmentOthers.setId(id);
        return taskAttachmentOthers;
    }
}
