package com.cmspro.base.microservice.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.cmspro.base.microservice.domain.UserRoles} entity.
 */
public class UserRolesDTO implements Serializable {
    
    private String id;

    @NotNull
    private String code;

    @NotNull
    private String description;

    private Boolean isActive;


    private String userGroupId;

    private String userGroupDescription;
    
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

    public String getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(String userGroupsId) {
        this.userGroupId = userGroupsId;
    }

    public String getUserGroupDescription() {
        return userGroupDescription;
    }

    public void setUserGroupDescription(String userGroupsDescription) {
        this.userGroupDescription = userGroupsDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserRolesDTO)) {
            return false;
        }

        return id != null && id.equals(((UserRolesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserRolesDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", isActive='" + isIsActive() + "'" +
            ", userGroupId='" + getUserGroupId() + "'" +
            ", userGroupDescription='" + getUserGroupDescription() + "'" +
            "}";
    }
}
