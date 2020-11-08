package com.cmspro.base.microservice.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.cmspro.base.microservice.domain.RFITimeLine} entity.
 */
public class RFITimeLineDTO implements Serializable {
    
    private String id;

    @NotNull
    private String createdBy;

    @NotNull
    private String message;

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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
        if (!(o instanceof RFITimeLineDTO)) {
            return false;
        }

        return id != null && id.equals(((RFITimeLineDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RFITimeLineDTO{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", message='" + getMessage() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", rfiId='" + getRfiId() + "'" +
            ", rfiTitle='" + getRfiTitle() + "'" +
            "}";
    }
}
