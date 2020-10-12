package com.cmspro.basemicroservice.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.cmspro.basemicroservice.domain.ProjectStatus} entity.
 */
public class ProjectStatusDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private String description;

    @NotNull
    private Boolean isActive;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProjectStatusDTO projectStatusDTO = (ProjectStatusDTO) o;
        if (projectStatusDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), projectStatusDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProjectStatusDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", isActive='" + isIsActive() + "'" +
            "}";
    }
}
