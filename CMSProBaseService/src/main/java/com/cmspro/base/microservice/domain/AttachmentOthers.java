package com.cmspro.base.microservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A AttachmentOthers.
 */
@Document(collection = "attachment_others")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "attachmentothers")
public class AttachmentOthers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("file_name")
    private String fileName;

    @Field("file_type")
    private String fileType;

    @Field("content")
    private byte[] content;

    @Field("content_content_type")
    private String contentContentType;

    @NotNull
    @Field("created_by")
    private String createdBy;

    @NotNull
    @Field("creation_date")
    private LocalDate creationDate;

    @DBRef
    @Field("attachment")
    @com.fasterxml.jackson.annotation.JsonBackReference
    private Attachments attachment;

    @DBRef
    @Field("rfi")
    @JsonIgnoreProperties(value = "sheetAttachments", allowSetters = true)
    private RFI rfi;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public AttachmentOthers fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public AttachmentOthers fileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getContent() {
        return content;
    }

    public AttachmentOthers content(byte[] content) {
        this.content = content;
        return this;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContentContentType() {
        return contentContentType;
    }

    public AttachmentOthers contentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
        return this;
    }

    public void setContentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public AttachmentOthers createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public AttachmentOthers creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Attachments getAttachment() {
        return attachment;
    }

    public AttachmentOthers attachment(Attachments attachments) {
        this.attachment = attachments;
        return this;
    }

    public void setAttachment(Attachments attachments) {
        this.attachment = attachments;
    }

    public RFI getRfi() {
        return rfi;
    }

    public AttachmentOthers rfi(RFI rFI) {
        this.rfi = rFI;
        return this;
    }

    public void setRfi(RFI rFI) {
        this.rfi = rFI;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttachmentOthers)) {
            return false;
        }
        return id != null && id.equals(((AttachmentOthers) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttachmentOthers{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", fileType='" + getFileType() + "'" +
            ", content='" + getContent() + "'" +
            ", contentContentType='" + getContentContentType() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}
