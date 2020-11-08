package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.TaskCommentsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaskComments} and its DTO {@link TaskCommentsDTO}.
 */
@Mapper(componentModel = "spring", uses = {TasksMapper.class})
public interface TaskCommentsMapper extends EntityMapper<TaskCommentsDTO, TaskComments> {

    @Mapping(source = "task.id", target = "taskId")
    @Mapping(source = "task.title", target = "taskTitle")
    TaskCommentsDTO toDto(TaskComments taskComments);

    @Mapping(source = "taskId", target = "task")
    TaskComments toEntity(TaskCommentsDTO taskCommentsDTO);

    default TaskComments fromId(String id) {
        if (id == null) {
            return null;
        }
        TaskComments taskComments = new TaskComments();
        taskComments.setId(id);
        return taskComments;
    }
}
