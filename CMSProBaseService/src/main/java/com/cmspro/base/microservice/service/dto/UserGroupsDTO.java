package com.cmspro.base.microservice.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.cmspro.base.microservice.domain.UserGroups} entity.
 */
public class UserGroupsDTO implements Serializable {
    
    private String id;

    @NotNull
    private String code;

    @NotNull
    private String description;


    private String accountId;

    private String accountAccountNumber;

    private String usersId;

    private String usersFullName;
    
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

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountsId) {
        this.accountId = accountsId;
    }

    public String getAccountAccountNumber() {
        return accountAccountNumber;
    }

    public void setAccountAccountNumber(String accountsAccountNumber) {
        this.accountAccountNumber = accountsAccountNumber;
    }

    public String getUsersId() {
        return usersId;
    }

    public void setUsersId(String usersId) {
        this.usersId = usersId;
    }

    public String getUsersFullName() {
        return usersFullName;
    }

    public void setUsersFullName(String usersFullName) {
        this.usersFullName = usersFullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserGroupsDTO)) {
            return false;
        }

        return id != null && id.equals(((UserGroupsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserGroupsDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", accountId='" + getAccountId() + "'" +
            ", accountAccountNumber='" + getAccountAccountNumber() + "'" +
            ", usersId='" + getUsersId() + "'" +
            ", usersFullName='" + getUsersFullName() + "'" +
            "}";
    }
}
