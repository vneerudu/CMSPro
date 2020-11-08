package com.cmspro.base.microservice.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.cmspro.base.microservice.domain.TaskComments} entity.
 */
public class TaskCommentsDTO implements Serializable {
    
    private String id;

    @NotNull
    private String createdBy;

    @NotNull
    private String comment;

    @NotNull
    private LocalDate creationDate;


    private String taskId;

    private String taskTitle;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String tasksId) {
        this.taskId = tasksId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String tasksTitle) {
        this.taskTitle = tasksTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskCommentsDTO)) {
            return false;
        }

        return id != null && id.equals(((TaskCommentsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskCommentsDTO{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", comment='" + getComment() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", taskId='" + getTaskId() + "'" +
            ", taskTitle='" + getTaskTitle() + "'" +
            "}";
    }
}
