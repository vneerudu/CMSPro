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
 * A UserGroups.
 */
@Document(collection = "user_groups")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "usergroups")
public class UserGroups implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("code")
    private String code;

    @NotNull
    @Field("description")
    private String description;

    @DBRef
    @Field("userRoles")
    private Set<UserRoles> userRoles = new HashSet<>();

    @DBRef
    @Field("account")
    @JsonIgnoreProperties(value = "groups", allowSetters = true)
    private Accounts account;

    @DBRef
    @Field("users")
    @JsonIgnoreProperties(value = "userGroups", allowSetters = true)
    private Users users;

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

    public UserGroups code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public UserGroups description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<UserRoles> getUserRoles() {
        return userRoles;
    }

    public UserGroups userRoles(Set<UserRoles> userRoles) {
        this.userRoles = userRoles;
        return this;
    }

    public UserGroups addUserRoles(UserRoles userRoles) {
        this.userRoles.add(userRoles);
        userRoles.setUserGroup(this);
        return this;
    }

    public UserGroups removeUserRoles(UserRoles userRoles) {
        this.userRoles.remove(userRoles);
        userRoles.setUserGroup(null);
        return this;
    }

    public void setUserRoles(Set<UserRoles> userRoles) {
        this.userRoles = userRoles;
    }

    public Accounts getAccount() {
        return account;
    }

    public UserGroups account(Accounts accounts) {
        this.account = accounts;
        return this;
    }

    public void setAccount(Accounts accounts) {
        this.account = accounts;
    }

    public Users getUsers() {
        return users;
    }

    public UserGroups users(Users users) {
        this.users = users;
        return this;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserGroups)) {
            return false;
        }
        return id != null && id.equals(((UserGroups) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserGroups{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
