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
 * A RFITimeLine.
 */
@Document(collection = "rfi_time_line")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "rfitimeline")
public class RFITimeLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("created_by")
    private String createdBy;

    @NotNull
    @Field("message")
    private String message;

    @NotNull
    @Field("creation_date")
    private LocalDate creationDate;

    @DBRef
    @Field("rfi")
    @JsonIgnoreProperties(value = "timeLines", allowSetters = true)
    private RFI rfi;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public RFITimeLine createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getMessage() {
        return message;
    }

    public RFITimeLine message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public RFITimeLine creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public RFI getRfi() {
        return rfi;
    }

    public RFITimeLine rfi(RFI rFI) {
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
        if (!(o instanceof RFITimeLine)) {
            return false;
        }
        return id != null && id.equals(((RFITimeLine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RFITimeLine{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", message='" + getMessage() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}
