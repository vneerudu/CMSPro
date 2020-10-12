package com.cmspro.basemicroservice.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Projects.
 */
@Entity
@Table(name = "projects")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "projects")
public class Projects implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "project_id", nullable = false, unique = true)
    private Long projectID;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "department")
    private String department;

    @Column(name = "organization")
    private String organization;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "finish_date")
    private LocalDate finishDate;

    @ManyToOne
    @JsonIgnoreProperties("projects")
    private ProjectStatus projectStatusRel;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectID() {
        return projectID;
    }

    public Projects projectID(Long projectID) {
        this.projectID = projectID;
        return this;
    }

    public void setProjectID(Long projectID) {
        this.projectID = projectID;
    }

    public String getName() {
        return name;
    }

    public Projects name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public Projects department(String department) {
        this.department = department;
        return this;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getOrganization() {
        return organization;
    }

    public Projects organization(String organization) {
        this.organization = organization;
        return this;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Projects startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public Projects finishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
        return this;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public ProjectStatus getProjectStatusRel() {
        return projectStatusRel;
    }

    public Projects projectStatusRel(ProjectStatus projectStatus) {
        this.projectStatusRel = projectStatus;
        return this;
    }

    public void setProjectStatusRel(ProjectStatus projectStatus) {
        this.projectStatusRel = projectStatus;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Projects)) {
            return false;
        }
        return id != null && id.equals(((Projects) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Projects{" +
            "id=" + getId() +
            ", projectID=" + getProjectID() +
            ", name='" + getName() + "'" +
            ", department='" + getDepartment() + "'" +
            ", organization='" + getOrganization() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", finishDate='" + getFinishDate() + "'" +
            "}";
    }
}
