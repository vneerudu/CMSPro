package com.cmspro.basemicroservice.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.cmspro.basemicroservice.domain.Projects} entity. This class is used
 * in {@link com.cmspro.basemicroservice.web.rest.ProjectsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /projects?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProjectsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter projectID;

    private StringFilter name;

    private StringFilter department;

    private StringFilter organization;

    private LocalDateFilter startDate;

    private LocalDateFilter finishDate;

    private LongFilter projectStatusRelId;

    public ProjectsCriteria(){
    }

    public ProjectsCriteria(ProjectsCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.projectID = other.projectID == null ? null : other.projectID.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.department = other.department == null ? null : other.department.copy();
        this.organization = other.organization == null ? null : other.organization.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.finishDate = other.finishDate == null ? null : other.finishDate.copy();
        this.projectStatusRelId = other.projectStatusRelId == null ? null : other.projectStatusRelId.copy();
    }

    @Override
    public ProjectsCriteria copy() {
        return new ProjectsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getProjectID() {
        return projectID;
    }

    public void setProjectID(LongFilter projectID) {
        this.projectID = projectID;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDepartment() {
        return department;
    }

    public void setDepartment(StringFilter department) {
        this.department = department;
    }

    public StringFilter getOrganization() {
        return organization;
    }

    public void setOrganization(StringFilter organization) {
        this.organization = organization;
    }

    public LocalDateFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateFilter startDate) {
        this.startDate = startDate;
    }

    public LocalDateFilter getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDateFilter finishDate) {
        this.finishDate = finishDate;
    }

    public LongFilter getProjectStatusRelId() {
        return projectStatusRelId;
    }

    public void setProjectStatusRelId(LongFilter projectStatusRelId) {
        this.projectStatusRelId = projectStatusRelId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProjectsCriteria that = (ProjectsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(projectID, that.projectID) &&
            Objects.equals(name, that.name) &&
            Objects.equals(department, that.department) &&
            Objects.equals(organization, that.organization) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(finishDate, that.finishDate) &&
            Objects.equals(projectStatusRelId, that.projectStatusRelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        projectID,
        name,
        department,
        organization,
        startDate,
        finishDate,
        projectStatusRelId
        );
    }

    @Override
    public String toString() {
        return "ProjectsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (projectID != null ? "projectID=" + projectID + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (department != null ? "department=" + department + ", " : "") +
                (organization != null ? "organization=" + organization + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (finishDate != null ? "finishDate=" + finishDate + ", " : "") +
                (projectStatusRelId != null ? "projectStatusRelId=" + projectStatusRelId + ", " : "") +
            "}";
    }

}
