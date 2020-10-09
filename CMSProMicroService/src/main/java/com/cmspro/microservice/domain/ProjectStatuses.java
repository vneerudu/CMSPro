package com.cmspro.microservice.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A ProjectStatuses.
 */
@Entity
@Table(name = "project_statuses")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "projectstatuses")
public class ProjectStatuses implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "status_id", nullable = false, unique = true)
    private Long status_id;

    @NotNull
    @Column(name = "status_code", nullable = false, unique = true)
    private String status_code;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "is_active", nullable = false)
    private Boolean is_active;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStatus_id() {
        return status_id;
    }

    public ProjectStatuses status_id(Long status_id) {
        this.status_id = status_id;
        return this;
    }

    public void setStatus_id(Long status_id) {
        this.status_id = status_id;
    }

    public String getStatus_code() {
        return status_code;
    }

    public ProjectStatuses status_code(String status_code) {
        this.status_code = status_code;
        return this;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getDescription() {
        return description;
    }

    public ProjectStatuses description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isIs_active() {
        return is_active;
    }

    public ProjectStatuses is_active(Boolean is_active) {
        this.is_active = is_active;
        return this;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectStatuses)) {
            return false;
        }
        return id != null && id.equals(((ProjectStatuses) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectStatuses{" +
            "id=" + getId() +
            ", status_id=" + getStatus_id() +
            ", status_code='" + getStatus_code() + "'" +
            ", description='" + getDescription() + "'" +
            ", is_active='" + isIs_active() + "'" +
            "}";
    }
}
