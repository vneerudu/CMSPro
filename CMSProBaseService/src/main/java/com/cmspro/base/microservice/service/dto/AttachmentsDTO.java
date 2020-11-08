package com.cmspro.base.microservice.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.cmspro.base.microservice.domain.Attachments} entity.
 */
public class AttachmentsDTO implements Serializable {
    
    private String id;

    private String folder;

    @NotNull
    private String fileName;

    @NotNull
    private String createdBy;

    @NotNull
    private LocalDate creationDate;


    private String imageId;

    private String imageFileName;

    private String pdfattachmentId;

    private String pdfattachmentFileName;

    private String sheetId;

    private String sheetNumber;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String attachmentImagesId) {
        this.imageId = attachmentImagesId;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String attachmentImagesFileName) {
        this.imageFileName = attachmentImagesFileName;
    }

    public String getPdfattachmentId() {
        return pdfattachmentId;
    }

    public void setPdfattachmentId(String attachmentOthersId) {
        this.pdfattachmentId = attachmentOthersId;
    }

    public String getPdfattachmentFileName() {
        return pdfattachmentFileName;
    }

    public void setPdfattachmentFileName(String attachmentOthersFileName) {
        this.pdfattachmentFileName = attachmentOthersFileName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttachmentsDTO)) {
            return false;
        }

        return id != null && id.equals(((AttachmentsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttachmentsDTO{" +
            "id=" + getId() +
            ", folder='" + getFolder() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", imageId='" + getImageId() + "'" +
            ", imageFileName='" + getImageFileName() + "'" +
            ", pdfattachmentId='" + getPdfattachmentId() + "'" +
            ", pdfattachmentFileName='" + getPdfattachmentFileName() + "'" +
            ", sheetId='" + getSheetId() + "'" +
            ", sheetNumber='" + getSheetNumber() + "'" +
            "}";
    }
}
