package com.cmspro.base.microservice.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.cmspro.base.microservice.domain.AttachmentImages} entity.
 */
public class AttachmentImagesDTO implements Serializable {
    
    private String id;

    @NotNull
    private String fileName;

    private String fileType;

    private byte[] content;

    private String contentContentType;
    @NotNull
    private String createdBy;

    @NotNull
    private LocalDate creationDate;


    private String rfiId;

    private String rfiTitle;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContentContentType() {
        return contentContentType;
    }

    public void setContentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
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

    public String getRfiId() {
        return rfiId;
    }

    public void setRfiId(String rFIId) {
        this.rfiId = rFIId;
    }

    public String getRfiTitle() {
        return rfiTitle;
    }

    public void setRfiTitle(String rFITitle) {
        this.rfiTitle = rFITitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttachmentImagesDTO)) {
            return false;
        }

        return id != null && id.equals(((AttachmentImagesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttachmentImagesDTO{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", fileType='" + getFileType() + "'" +
            ", content='" + getContent() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", rfiId='" + getRfiId() + "'" +
            ", rfiTitle='" + getRfiTitle() + "'" +
            "}";
    }
}
