package com.cmspro.base.microservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * A UserRoles.
 */
@Document(collection = "user_roles")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "userroles")
public class UserRoles implements Serializable {

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
    @Field("permissions")
    private Set<UserPermissions> permissions = new HashSet<>();

    @DBRef
    @Field("menuItems")
    private Set<MenuItems> menuItems = new HashSet<>();

    @DBRef
    @Field("userGroup")
    @JsonIgnoreProperties(value = "userRoles", allowSetters = true)
    private UserGroups userGroup;

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

    public UserRoles code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public UserRoles description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public UserRoles isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Set<UserPermissions> getPermissions() {
        return permissions;
    }

    public UserRoles permissions(Set<UserPermissions> userPermissions) {
        this.permissions = userPermissions;
        return this;
    }

    public UserRoles addPermissions(UserPermissions userPermissions) {
        this.permissions.add(userPermissions);
        userPermissions.setUserRoles(this);
        return this;
    }

    public UserRoles removePermissions(UserPermissions userPermissions) {
        this.permissions.remove(userPermissions);
        userPermissions.setUserRoles(null);
        return this;
    }

    public void setPermissions(Set<UserPermissions> userPermissions) {
        this.permissions = userPermissions;
    }

    public Set<MenuItems> getMenuItems() {
        return menuItems;
    }

    public UserRoles menuItems(Set<MenuItems> menuItems) {
        this.menuItems = menuItems;
        return this;
    }

    public UserRoles addMenuItems(MenuItems menuItems) {
        this.menuItems.add(menuItems);
        menuItems.setUserRoles(this);
        return this;
    }

    public UserRoles removeMenuItems(MenuItems menuItems) {
        this.menuItems.remove(menuItems);
        menuItems.setUserRoles(null);
        return this;
    }

    public void setMenuItems(Set<MenuItems> menuItems) {
        this.menuItems = menuItems;
    }

    public UserGroups getUserGroup() {
        return userGroup;
    }

    public UserRoles userGroup(UserGroups userGroups) {
        this.userGroup = userGroups;
        return this;
    }

    public void setUserGroup(UserGroups userGroups) {
        this.userGroup = userGroups;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserRoles)) {
            return false;
        }
        return id != null && id.equals(((UserRoles) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserRoles{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", isActive='" + isIsActive() + "'" +
            "}";
    }
}
