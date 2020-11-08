package com.cmspro.base.microservice.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.cmspro.base.microservice.domain.SheetComments} entity.
 */
public class SheetCommentsDTO implements Serializable {
    
    private String id;

    @NotNull
    private String createdBy;

    @NotNull
    private String comment;

    @NotNull
    private LocalDate creationDate;


    private String sheetId;

    private String sheetNumber;
    
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
        if (!(o instanceof SheetCommentsDTO)) {
            return false;
        }

        return id != null && id.equals(((SheetCommentsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SheetCommentsDTO{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", comment='" + getComment() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", sheetId='" + getSheetId() + "'" +
            ", sheetNumber='" + getSheetNumber() + "'" +
            "}";
    }
}
