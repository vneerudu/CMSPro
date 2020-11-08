package com.cmspro.base.microservice.service.mapper;


import com.cmspro.base.microservice.domain.*;
import com.cmspro.base.microservice.service.dto.TasksDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tasks} and its DTO {@link TasksDTO}.
 */
@Mapper(componentModel = "spring", uses = {TaskTypesMapper.class, TaskStatusesMapper.class, TaskLocationsMapper.class, StampsMapper.class, ListsMapper.class, SheetsMapper.class, RootCausesMapper.class, ProjectsMapper.class})
public interface TasksMapper extends EntityMapper<TasksDTO, Tasks> {

    @Mapping(source = "type.id", target = "typeId")
    @Mapping(source = "type.description", target = "typeDescription")
    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "status.description", target = "statusDescription")
    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "location.description", target = "locationDescription")
    @Mapping(source = "stamp.id", target = "stampId")
    @Mapping(source = "stamp.title", target = "stampTitle")
    @Mapping(source = "list.id", target = "listId")
    @Mapping(source = "list.description", target = "listDescription")
    @Mapping(source = "sheet.id", target = "sheetId")
    @Mapping(source = "sheet.number", target = "sheetNumber")
    @Mapping(source = "rootCauses.id", target = "rootCausesId")
    @Mapping(source = "rootCauses.description", target = "rootCausesDescription")
    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "project.name", target = "projectName")
    TasksDTO toDto(Tasks tasks);

    @Mapping(source = "typeId", target = "type")
    @Mapping(source = "statusId", target = "status")
    @Mapping(source = "locationId", target = "location")
    @Mapping(source = "stampId", target = "stamp")
    @Mapping(source = "listId", target = "list")
    @Mapping(source = "sheetId", target = "sheet")
    @Mapping(source = "rootCausesId", target = "rootCauses")
    @Mapping(target = "assignedTos", ignore = true)
    @Mapping(target = "removeAssignedTo", ignore = true)
    @Mapping(target = "monitors", ignore = true)
    @Mapping(target = "removeMonitors", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "removeImages", ignore = true)
    @Mapping(target = "othersAttachments", ignore = true)
    @Mapping(target = "removeOthersAttachment", ignore = true)
    @Mapping(target = "taskComments", ignore = true)
    @Mapping(target = "removeTaskComments", ignore = true)
    @Mapping(source = "projectId", target = "project")
    Tasks toEntity(TasksDTO tasksDTO);

    default Tasks fromId(String id) {
        if (id == null) {
            return null;
        }
        Tasks tasks = new Tasks();
        tasks.setId(id);
        return tasks;
    }
}
