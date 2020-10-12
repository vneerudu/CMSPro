package com.cmspro.basemicroservice.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.cmspro.basemicroservice.domain.Projects} entity.
 */
public class ProjectsDTO implements Serializable {

    private Long id;

    @NotNull
    private Long projectID;

    @NotNull
    private String name;

    private String department;

    private String organization;

    @NotNull
    private LocalDate startDate;

    private LocalDate finishDate;


    private Long projectStatusRelId;

    private String projectStatusRelDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectID() {
        return projectID;
    }

    public void setProjectID(Long projectID) {
        this.projectID = projectID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public Long getProjectStatusRelId() {
        return projectStatusRelId;
    }

    public void setProjectStatusRelId(Long projectStatusId) {
        this.projectStatusRelId = projectStatusId;
    }

    public String getProjectStatusRelDescription() {
        return projectStatusRelDescription;
    }

    public void setProjectStatusRelDescription(String projectStatusDescription) {
        this.projectStatusRelDescription = projectStatusDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProjectsDTO projectsDTO = (ProjectsDTO) o;
        if (projectsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), projectsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProjectsDTO{" +
            "id=" + getId() +
            ", projectID=" + getProjectID() +
            ", name='" + getName() + "'" +
            ", department='" + getDepartment() + "'" +
            ", organization='" + getOrganization() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", finishDate='" + getFinishDate() + "'" +
            ", projectStatusRel=" + getProjectStatusRelId() +
            ", projectStatusRel='" + getProjectStatusRelDescription() + "'" +
            "}";
    }
}
