package com.cmspro.base.microservice.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.cmspro.base.microservice.domain.RootCauses} entity.
 */
public class RootCausesDTO implements Serializable {
    
    private String id;

    @NotNull
    private String code;

    @NotNull
    private String description;

    private Boolean isActive;


    private String groupsId;

    private String groupsCode;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getGroupsId() {
        return groupsId;
    }

    public void setGroupsId(String rootCauseGroupsId) {
        this.groupsId = rootCauseGroupsId;
    }

    public String getGroupsCode() {
        return groupsCode;
    }

    public void setGroupsCode(String rootCauseGroupsCode) {
        this.groupsCode = rootCauseGroupsCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RootCausesDTO)) {
            return false;
        }

        return id != null && id.equals(((RootCausesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RootCausesDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", isActive='" + isIsActive() + "'" +
            ", groupsId='" + getGroupsId() + "'" +
            ", groupsCode='" + getGroupsCode() + "'" +
            "}";
    }
}
