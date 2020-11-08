package com.cmspro.base.microservice.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A TaskAttachments.
 */
@Document(collection = "task_attachments")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "taskattachments")
public class TaskAttachments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("folder")
    private String folder;

    @NotNull
    @Field("file_name")
    private String fileName;

    @NotNull
    @Field("created_by")
    private String createdBy;

    @NotNull
    @Field("creation_date")
    private LocalDate creationDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFolder() {
        return folder;
    }

    public TaskAttachments folder(String folder) {
        this.folder = folder;
        return this;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getFileName() {
        return fileName;
    }

    public TaskAttachments fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public TaskAttachments createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public TaskAttachments creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskAttachments)) {
            return false;
        }
        return id != null && id.equals(((TaskAttachments) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskAttachments{" +
            "id=" + getId() +
            ", folder='" + getFolder() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}
