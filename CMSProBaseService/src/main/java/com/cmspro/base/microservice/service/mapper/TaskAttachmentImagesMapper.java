package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.TaskAttachmentImagesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaskAttachmentImages} and its DTO {@link TaskAttachmentImagesDTO}.
 */
@Mapper(componentModel = "spring", uses = {TasksMapper.class, RFIMapper.class})
public interface TaskAttachmentImagesMapper extends EntityMapper<TaskAttachmentImagesDTO, TaskAttachmentImages> {

    @Mapping(source = "task.id", target = "taskId")
    @Mapping(source = "task.title", target = "taskTitle")
    @Mapping(source = "rfi.id", target = "rfiId")
    @Mapping(source = "rfi.title", target = "rfiTitle")
    TaskAttachmentImagesDTO toDto(TaskAttachmentImages taskAttachmentImages);

    @Mapping(source = "taskId", target = "task")
    @Mapping(source = "rfiId", target = "rfi")
    TaskAttachmentImages toEntity(TaskAttachmentImagesDTO taskAttachmentImagesDTO);

    default TaskAttachmentImages fromId(String id) {
        if (id == null) {
            return null;
        }
        TaskAttachmentImages taskAttachmentImages = new TaskAttachmentImages();
        taskAttachmentImages.setId(id);
        return taskAttachmentImages;
    }
}
