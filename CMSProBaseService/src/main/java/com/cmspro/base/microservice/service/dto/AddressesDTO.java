package com.cmspro.base.microservice.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.cmspro.base.microservice.domain.Addresses} entity.
 */
public class AddressesDTO implements Serializable {
    
    private String id;

    private String address1;

    private String address2;

    private String city;

    private Long zipCode;

    private Boolean isActive;

    @NotNull
    private String createdBy;

    @NotNull
    private LocalDate creationDate;


    private String addressTypeId;

    private String addressTypeDescription;

    private String stateId;

    private String stateDescription;

    private String countryId;

    private String countryDescription;

    private String userId;

    private String userFullName;

    private String projectId;

    private String projectName;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getZipCode() {
        return zipCode;
    }

    public void setZipCode(Long zipCode) {
        this.zipCode = zipCode;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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

    public String getAddressTypeId() {
        return addressTypeId;
    }

    public void setAddressTypeId(String addressTypesId) {
        this.addressTypeId = addressTypesId;
    }

    public String getAddressTypeDescription() {
        return addressTypeDescription;
    }

    public void setAddressTypeDescription(String addressTypesDescription) {
        this.addressTypeDescription = addressTypesDescription;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String statesId) {
        this.stateId = statesId;
    }

    public String getStateDescription() {
        return stateDescription;
    }

    public void setStateDescription(String statesDescription) {
        this.stateDescription = statesDescription;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountryDescription() {
        return countryDescription;
    }

    public void setCountryDescription(String countryDescription) {
        this.countryDescription = countryDescription;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String usersId) {
        this.userId = usersId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String usersFullName) {
        this.userFullName = usersFullName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectsId) {
        this.projectId = projectsId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectsName) {
        this.projectName = projectsName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AddressesDTO)) {
            return false;
        }

        return id != null && id.equals(((AddressesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AddressesDTO{" +
            "id=" + getId() +
            ", address1='" + getAddress1() + "'" +
            ", address2='" + getAddress2() + "'" +
            ", city='" + getCity() + "'" +
            ", zipCode=" + getZipCode() +
            ", isActive='" + isIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", addressTypeId='" + getAddressTypeId() + "'" +
            ", addressTypeDescription='" + getAddressTypeDescription() + "'" +
            ", stateId='" + getStateId() + "'" +
            ", stateDescription='" + getStateDescription() + "'" +
            ", countryId='" + getCountryId() + "'" +
            ", countryDescription='" + getCountryDescription() + "'" +
            ", userId='" + getUserId() + "'" +
            ", userFullName='" + getUserFullName() + "'" +
            ", projectId='" + getProjectId() + "'" +
            ", projectName='" + getProjectName() + "'" +
            "}";
    }
}
