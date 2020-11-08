package com.cmspro.base.microservice.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.cmspro.base.microservice.domain.Accounts} entity.
 */
public class AccountsDTO implements Serializable {
    
    private String id;

    @NotNull
    private Long accountNumber;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String emailAddress;

    @NotNull
    private String phoneNumber;

    @NotNull
    private LocalDate creationDate;

    @NotNull
    private String createdBy;


    private String languageId;

    private String languageCode;

    private String logoId;

    private String logoFileName;

    private String statusId;

    private String statusDescription;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languagesId) {
        this.languageId = languagesId;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languagesCode) {
        this.languageCode = languagesCode;
    }

    public String getLogoId() {
        return logoId;
    }

    public void setLogoId(String logosId) {
        this.logoId = logosId;
    }

    public String getLogoFileName() {
        return logoFileName;
    }

    public void setLogoFileName(String logosFileName) {
        this.logoFileName = logosFileName;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String accountStatusesId) {
        this.statusId = accountStatusesId;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String accountStatusesDescription) {
        this.statusDescription = accountStatusesDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountsDTO)) {
            return false;
        }

        return id != null && id.equals(((AccountsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccountsDTO{" +
            "id=" + getId() +
            ", accountNumber=" + getAccountNumber() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", emailAddress='" + getEmailAddress() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", languageId='" + getLanguageId() + "'" +
            ", languageCode='" + getLanguageCode() + "'" +
            ", logoId='" + getLogoId() + "'" +
            ", logoFileName='" + getLogoFileName() + "'" +
            ", statusId='" + getStatusId() + "'" +
            ", statusDescription='" + getStatusDescription() + "'" +
            "}";
    }
}
