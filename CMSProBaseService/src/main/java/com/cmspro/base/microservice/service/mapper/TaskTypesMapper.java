package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.TaskTypesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaskTypes} and its DTO {@link TaskTypesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TaskTypesMapper extends EntityMapper<TaskTypesDTO, TaskTypes> {


    @Mapping(target = "task", ignore = true)
    TaskTypes toEntity(TaskTypesDTO taskTypesDTO);

    default TaskTypes fromId(String id) {
        if (id == null) {
            return null;
        }
        TaskTypes taskTypes = new TaskTypes();
        taskTypes.setId(id);
        return taskTypes;
    }
}
