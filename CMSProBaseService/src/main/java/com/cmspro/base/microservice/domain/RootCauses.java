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
 * A RootCauses.
 */
@Document(collection = "root_causes")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "rootcauses")
public class RootCauses implements Serializable {

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
    @Field("groups")
    @JsonIgnoreProperties(value = "rootCauses", allowSetters = true)
    private RootCauseGroups groups;

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

    public RootCauses code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public RootCauses description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public RootCauses isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Tasks getTask() {
        return task;
    }

    public RootCauses task(Tasks tasks) {
        this.task = tasks;
        return this;
    }

    public void setTask(Tasks tasks) {
        this.task = tasks;
    }

    public RootCauseGroups getGroups() {
        return groups;
    }

    public RootCauses groups(RootCauseGroups rootCauseGroups) {
        this.groups = rootCauseGroups;
        return this;
    }

    public void setGroups(RootCauseGroups rootCauseGroups) {
        this.groups = rootCauseGroups;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RootCauses)) {
            return false;
        }
        return id != null && id.equals(((RootCauses) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RootCauses{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", isActive='" + isIsActive() + "'" +
            "}";
    }
}
