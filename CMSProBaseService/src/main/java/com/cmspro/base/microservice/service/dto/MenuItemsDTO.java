package com.cmspro.base.microservice.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.cmspro.base.microservice.domain.MenuItems} entity.
 */
public class MenuItemsDTO implements Serializable {
    
    private String id;

    @NotNull
    private String code;

    @NotNull
    private String description;

    private Boolean isActive;


    private String userRolesId;

    private String userRolesDescription;
    
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

    public String getUserRolesId() {
        return userRolesId;
    }

    public void setUserRolesId(String userRolesId) {
        this.userRolesId = userRolesId;
    }

    public String getUserRolesDescription() {
        return userRolesDescription;
    }

    public void setUserRolesDescription(String userRolesDescription) {
        this.userRolesDescription = userRolesDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuItemsDTO)) {
            return false;
        }

        return id != null && id.equals(((MenuItemsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MenuItemsDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", isActive='" + isIsActive() + "'" +
            ", userRolesId='" + getUserRolesId() + "'" +
            ", userRolesDescription='" + getUserRolesDescription() + "'" +
            "}";
    }
}
