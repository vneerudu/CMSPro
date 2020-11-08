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
 * A SheetComments.
 */
@Document(collection = "sheet_comments")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "sheetcomments")
public class SheetComments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("created_by")
    private String createdBy;

    @NotNull
    @Field("comment")
    private String comment;

    @NotNull
    @Field("creation_date")
    private LocalDate creationDate;

    @DBRef
    @Field("sheet")
    @JsonIgnoreProperties(value = "comments", allowSetters = true)
    private Sheets sheet;

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

    public SheetComments createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getComment() {
        return comment;
    }

    public SheetComments comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public SheetComments creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Sheets getSheet() {
        return sheet;
    }

    public SheetComments sheet(Sheets sheets) {
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
        if (!(o instanceof SheetComments)) {
            return false;
        }
        return id != null && id.equals(((SheetComments) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SheetComments{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", comment='" + getComment() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}
