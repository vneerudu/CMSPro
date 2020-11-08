package com.cmspro.base.microservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Attachments.
 */
@Document(collection = "attachments")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "attachments")
public class Attachments implements Serializable {

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

    @DBRef
    @Field("image")
    private AttachmentImages image;

    @DBRef
    @Field("pdfattachment")
    private AttachmentOthers pdfattachment;

    @DBRef
    @Field("sheet")
    @JsonIgnoreProperties(value = "attachments", allowSetters = true)
    private Sheets sheet;

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

    public Attachments folder(String folder) {
        this.folder = folder;
        return this;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getFileName() {
        return fileName;
    }

    public Attachments fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Attachments createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Attachments creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public AttachmentImages getImage() {
        return image;
    }

    public Attachments image(AttachmentImages attachmentImages) {
        this.image = attachmentImages;
        return this;
    }

    public void setImage(AttachmentImages attachmentImages) {
        this.image = attachmentImages;
    }

    public AttachmentOthers getPdfattachment() {
        return pdfattachment;
    }

    public Attachments pdfattachment(AttachmentOthers attachmentOthers) {
        this.pdfattachment = attachmentOthers;
        return this;
    }

    public void setPdfattachment(AttachmentOthers attachmentOthers) {
        this.pdfattachment = attachmentOthers;
    }

    public Sheets getSheet() {
        return sheet;
    }

    public Attachments sheet(Sheets sheets) {
        this.sheet = sheets;
        return this;
    }

    public void setSheet(Sheets sheets) {
        this.sheet = sheets;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attachments)) {
            return false;
        }
        return id != null && id.equals(((Attachments) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Attachments{" +
            "id=" + getId() +
            ", folder='" + getFolder() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}
