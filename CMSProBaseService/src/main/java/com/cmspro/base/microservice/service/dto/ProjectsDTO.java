package com.cmspro.base.microservice.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.cmspro.base.microservice.domain.Projects} entity.
 */
public class ProjectsDTO implements Serializable {
    
    private String id;

    private String code;

    @NotNull
    private String name;

    private LocalDate startDate;

    private LocalDate finishDate;

    private LocalDate lastUpdate;

    @NotNull
    private String createdBy;

    @NotNull
    private LocalDate creationDate;


    private String accountId;

    private String accountAccountNumber;
    
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectsDTO)) {
            return false;
        }

        return id != null && id.equals(((ProjectsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectsDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", finishDate='" + getFinishDate() + "'" +
            ", lastUpdate='" + getLastUpdate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", accountId='" + getAccountId() + "'" +
            ", accountAccountNumber='" + getAccountAccountNumber() + "'" +
            "}";
    }
}
