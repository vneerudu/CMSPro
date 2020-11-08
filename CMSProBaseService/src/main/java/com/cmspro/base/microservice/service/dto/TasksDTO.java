package com.cmspro.base.microservice.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.cmspro.base.microservice.domain.Tasks} entity.
 */
public class TasksDTO implements Serializable {
    
    private String id;

    @NotNull
    private String title;

    private LocalDate startDate;

    private LocalDate dueDate;

    private String description;

    private Boolean costImpact;

    private String costImpactComment;

    private Boolean scheduleImpact;

    private String scheduleImpactComment;

    @NotNull
    private String createdBy;

    @NotNull
    private LocalDate creationDate;


    private String typeId;

    private String typeDescription;

    private String statusId;

    private String statusDescription;

    private String locationId;

    private String locationDescription;

    private String stampId;

    private String stampTitle;

    private String listId;

    private String listDescription;

    private String sheetId;

    private String sheetNumber;

    private String rootCausesId;

    private String rootCausesDescription;

    private String projectId;

    private String projectName;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isCostImpact() {
        return costImpact;
    }

    public void setCostImpact(Boolean costImpact) {
        this.costImpact = costImpact;
    }

    public String getCostImpactComment() {
        return costImpactComment;
    }

    public void setCostImpactComment(String costImpactComment) {
        this.costImpactComment = costImpactComment;
    }

    public Boolean isScheduleImpact() {
        return scheduleImpact;
    }

    public void setScheduleImpact(Boolean scheduleImpact) {
        this.scheduleImpact = scheduleImpact;
    }

    public String getScheduleImpactComment() {
        return scheduleImpactComment;
    }

    public void setScheduleImpactComment(String scheduleImpactComment) {
        this.scheduleImpactComment = scheduleImpactComment;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String taskTypesId) {
        this.typeId = taskTypesId;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String taskTypesDescription) {
        this.typeDescription = taskTypesDescription;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String taskStatusesId) {
        this.statusId = taskStatusesId;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String taskStatusesDescription) {
        this.statusDescription = taskStatusesDescription;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String taskLocationsId) {
        this.locationId = taskLocationsId;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String taskLocationsDescription) {
        this.locationDescription = taskLocationsDescription;
    }

    public String getStampId() {
        return stampId;
    }

    public void setStampId(String stampsId) {
        this.stampId = stampsId;
    }

    public String getStampTitle() {
        return stampTitle;
    }

    public void setStampTitle(String stampsTitle) {
        this.stampTitle = stampsTitle;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listsId) {
        this.listId = listsId;
    }

    public String getListDescription() {
        return listDescription;
    }

    public void setListDescription(String listsDescription) {
        this.listDescription = listsDescription;
    }

    public String getSheetId() {
        return sheetId;
    }

    public void setSheetId(String sheetsId) {
        this.sheetId = sheetsId;
    }

    public String getSheetNumber() {
        return sheetNumber;
    }

    public void setSheetNumber(String sheetsNumber) {
        this.sheetNumber = sheetsNumber;
    }

    public String getRootCausesId() {
        return rootCausesId;
    }

    public void setRootCausesId(String rootCausesId) {
        this.rootCausesId = rootCausesId;
    }

    public String getRootCausesDescription() {
        return rootCausesDescription;
    }

    public void setRootCausesDescription(String rootCausesDescription) {
        this.rootCausesDescription = rootCausesDescription;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectsId) {
        this.projectId = projectsId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectsName) {
        this.projectName = projectsName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TasksDTO)) {
            return false;
        }

        return id != null && id.equals(((TasksDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TasksDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", costImpact='" + isCostImpact() + "'" +
            ", costImpactComment='" + getCostImpactComment() + "'" +
            ", scheduleImpact='" + isScheduleImpact() + "'" +
            ", scheduleImpactComment='" + getScheduleImpactComment() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", typeId='" + getTypeId() + "'" +
            ", typeDescription='" + getTypeDescription() + "'" +
            ", statusId='" + getStatusId() + "'" +
            ", statusDescription='" + getStatusDescription() + "'" +
            ", locationId='" + getLocationId() + "'" +
            ", locationDescription='" + getLocationDescription() + "'" +
            ", stampId='" + getStampId() + "'" +
            ", stampTitle='" + getStampTitle() + "'" +
            ", listId='" + getListId() + "'" +
            ", listDescription='" + getListDescription() + "'" +
            ", sheetId='" + getSheetId() + "'" +
            ", sheetNumber='" + getSheetNumber() + "'" +
            ", rootCausesId='" + getRootCausesId() + "'" +
            ", rootCausesDescription='" + getRootCausesDescription() + "'" +
            ", projectId='" + getProjectId() + "'" +
            ", projectName='" + getProjectName() + "'" +
            "}";
    }
}
