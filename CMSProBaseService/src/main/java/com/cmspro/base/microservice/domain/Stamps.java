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
 * A Stamps.
 */
@Document(collection = "stamps")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "stamps")
public class Stamps implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("stamp")
    private String stamp;

    @NotNull
    @Field("title")
    private String title;

    @NotNull
    @Field("created_by")
    private String createdBy;

    @NotNull
    @Field("creation_date")
    private LocalDate creationDate;

    @DBRef
    @Field("task")
    @com.fasterxml.jackson.annotation.JsonBackReference
    private Tasks task;

    @DBRef
    @Field("project")
    @JsonIgnoreProperties(value = "stamps", allowSetters = true)
    private Projects project;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStamp() {
        return stamp;
    }

    public Stamps stamp(String stamp) {
        this.stamp = stamp;
        return this;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String getTitle() {
        return title;
    }

    public Stamps title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Stamps createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Stamps creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Tasks getTask() {
        return task;
    }

    public Stamps task(Tasks tasks) {
        this.task = tasks;
        return this;
    }

    public void setTask(Tasks tasks) {
        this.task = tasks;
    }

    public Projects getProject() {
        return project;
    }

    public Stamps project(Projects projects) {
        this.project = projects;
        return this;
    }

    public void setProject(Projects projects) {
        this.project = projects;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Stamps)) {
            return false;
        }
        return id != null && id.equals(((Stamps) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Stamps{" +
            "id=" + getId() +
            ", stamp='" + getStamp() + "'" +
            ", title='" + getTitle() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}
