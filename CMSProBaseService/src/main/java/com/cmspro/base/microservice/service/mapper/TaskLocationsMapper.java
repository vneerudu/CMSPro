package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.TaskLocationsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaskLocations} and its DTO {@link TaskLocationsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TaskLocationsMapper extends EntityMapper<TaskLocationsDTO, TaskLocations> {


    @Mapping(target = "task", ignore = true)
    TaskLocations toEntity(TaskLocationsDTO taskLocationsDTO);

    default TaskLocations fromId(String id) {
        if (id == null) {
            return null;
        }
        TaskLocations taskLocations = new TaskLocations();
        taskLocations.setId(id);
        return taskLocations;
    }
}
