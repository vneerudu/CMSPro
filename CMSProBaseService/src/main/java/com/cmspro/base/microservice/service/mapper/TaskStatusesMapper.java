package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.TaskStatusesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaskStatuses} and its DTO {@link TaskStatusesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TaskStatusesMapper extends EntityMapper<TaskStatusesDTO, TaskStatuses> {


    @Mapping(target = "task", ignore = true)
    TaskStatuses toEntity(TaskStatusesDTO taskStatusesDTO);

    default TaskStatuses fromId(String id) {
        if (id == null) {
            return null;
        }
        TaskStatuses taskStatuses = new TaskStatuses();
        taskStatuses.setId(id);
        return taskStatuses;
    }
}
