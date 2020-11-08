package com.cmspro.base.microservice.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A RootCauseGroups.
 */
@Document(collection = "root_cause_groups")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "rootcausegroups")
public class RootCauseGroups implements Serializable {

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
    @Field("rootCause")
    private Set<RootCauses> rootCauses = new HashSet<>();

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

    public RootCauseGroups code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public RootCauseGroups description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public RootCauseGroups isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Set<RootCauses> getRootCauses() {
        return rootCauses;
    }

    public RootCauseGroups rootCauses(Set<RootCauses> rootCauses) {
        this.rootCauses = rootCauses;
        return this;
    }

    public RootCauseGroups addRootCause(RootCauses rootCauses) {
        this.rootCauses.add(rootCauses);
        rootCauses.setGroups(this);
        return this;
    }

    public RootCauseGroups removeRootCause(RootCauses rootCauses) {
        this.rootCauses.remove(rootCauses);
        rootCauses.setGroups(null);
        return this;
    }

    public void setRootCauses(Set<RootCauses> rootCauses) {
        this.rootCauses = rootCauses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RootCauseGroups)) {
            return false;
        }
        return id != null && id.equals(((RootCauseGroups) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RootCauseGroups{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", isActive='" + isIsActive() + "'" +
            "}";
    }
}
