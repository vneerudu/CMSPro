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

/**
 * A Lists.
 */
@Document(collection = "lists")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "lists")
public class Lists implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("code")
    private String code;

    @NotNull
    @Field("description")
    private String description;

    @Field("is_active")
    private Boolean isActive;

    @DBRef
    @Field("task")
    @com.fasterxml.jackson.annotation.JsonBackReference
    private Tasks task;

    @DBRef
    @Field("project")
    @JsonIgnoreProperties(value = "lists", allowSetters = true)
    private Projects project;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Lists code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public Lists description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public Lists isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Tasks getTask() {
        return task;
    }

    public Lists task(Tasks tasks) {
        this.task = tasks;
        return this;
    }

    public void setTask(Tasks tasks) {
        this.task = tasks;
    }

    public Projects getProject() {
        return project;
    }

    public Lists project(Projects projects) {
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
        if (!(o instanceof Lists)) {
            return false;
        }
        return id != null && id.equals(((Lists) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Lists{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", isActive='" + isIsActive() + "'" +
            "}";
    }
}
