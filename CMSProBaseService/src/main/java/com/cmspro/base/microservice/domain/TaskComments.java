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
 * A TaskComments.
 */
@Document(collection = "task_comments")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "taskcomments")
public class TaskComments implements Serializable {

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
    @Field("task")
    @JsonIgnoreProperties(value = "taskComments", allowSetters = true)
    private Tasks task;

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

    public TaskComments createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getComment() {
        return comment;
    }

    public TaskComments comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public TaskComments creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Tasks getTask() {
        return task;
    }

    public TaskComments task(Tasks tasks) {
        this.task = tasks;
        return this;
    }

    public void setTask(Tasks tasks) {
        this.task = tasks;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskComments)) {
            return false;
        }
        return id != null && id.equals(((TaskComments) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskComments{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", comment='" + getComment() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}
