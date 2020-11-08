package com.cmspro.base.microservice.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.cmspro.base.microservice.domain.Stamps} entity.
 */
public class StampsDTO implements Serializable {
    
    private String id;

    @NotNull
    private String stamp;

    @NotNull
    private String title;

    @NotNull
    private String createdBy;

    @NotNull
    private LocalDate creationDate;


    private String projectId;

    private String projectName;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        if (!(o instanceof StampsDTO)) {
            return false;
        }

        return id != null && id.equals(((StampsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StampsDTO{" +
            "id=" + getId() +
            ", stamp='" + getStamp() + "'" +
            ", title='" + getTitle() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", projectId='" + getProjectId() + "'" +
            ", projectName='" + getProjectName() + "'" +
            "}";
    }
}
