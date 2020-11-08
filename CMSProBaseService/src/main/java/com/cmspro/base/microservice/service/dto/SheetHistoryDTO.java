package com.cmspro.base.microservice.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.cmspro.base.microservice.domain.SheetHistory} entity.
 */
public class SheetHistoryDTO implements Serializable {
    
    private String id;

    @NotNull
    private Long number;

    private String version;

    @NotNull
    private Boolean isActive;

    @NotNull
    private String createdBy;

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

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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
        if (!(o instanceof SheetHistoryDTO)) {
            return false;
        }

        return id != null && id.equals(((SheetHistoryDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SheetHistoryDTO{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", version='" + getVersion() + "'" +
            ", isActive='" + isIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", sheetId='" + getSheetId() + "'" +
            ", sheetNumber='" + getSheetNumber() + "'" +
            "}";
    }
}
