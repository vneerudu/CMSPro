package com.cmspro.base.microservice.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.cmspro.base.microservice.domain.Sheets} entity.
 */
public class SheetsDTO implements Serializable {
    
    private String id;

    @NotNull
    private Long number;

    private String title;

    @NotNull
    private String version;

    @NotNull
    private String createdBy;

    @NotNull
    private LocalDate creationDate;


    private String documentsId;

    private String projectId;

    private String projectName;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public String getDocumentsId() {
        return documentsId;
    }

    public void setDocumentsId(String documentsId) {
        this.documentsId = documentsId;
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
        if (!(o instanceof SheetsDTO)) {
            return false;
        }

        return id != null && id.equals(((SheetsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SheetsDTO{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", title='" + getTitle() + "'" +
            ", version='" + getVersion() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", documentsId='" + getDocumentsId() + "'" +
            ", projectId='" + getProjectId() + "'" +
            ", projectName='" + getProjectName() + "'" +
            "}";
    }
}
