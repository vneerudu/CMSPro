package com.cmspro.base.microservice.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.cmspro.base.microservice.domain.RFI} entity.
 */
public class RFIDTO implements Serializable {
    
    private String id;

    @NotNull
    private String title;

    @NotNull
    private String question;

    private String answer;

    private LocalDate sentDate;

    private LocalDate dueDate;

    private Boolean locked;

    private String lockedBy;


    private String statusId;

    private String statusDescription;

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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public LocalDate getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDate sentDate) {
        this.sentDate = sentDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean isLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getLockedBy() {
        return lockedBy;
    }

    public void setLockedBy(String lockedBy) {
        this.lockedBy = lockedBy;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String rFIStatusesId) {
        this.statusId = rFIStatusesId;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String rFIStatusesDescription) {
        this.statusDescription = rFIStatusesDescription;
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
        if (!(o instanceof RFIDTO)) {
            return false;
        }

        return id != null && id.equals(((RFIDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RFIDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", question='" + getQuestion() + "'" +
            ", answer='" + getAnswer() + "'" +
            ", sentDate='" + getSentDate() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", locked='" + isLocked() + "'" +
            ", lockedBy='" + getLockedBy() + "'" +
            ", statusId='" + getStatusId() + "'" +
            ", statusDescription='" + getStatusDescription() + "'" +
            ", projectId='" + getProjectId() + "'" +
            ", projectName='" + getProjectName() + "'" +
            "}";
    }
}
