package com.cmspro.base.microservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Addresses.
 */
@Document(collection = "addresses")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "addresses")
public class Addresses implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("address_1")
    private String address1;

    @Field("address_2")
    private String address2;

    @Field("city")
    private String city;

    @Field("zip_code")
    private Long zipCode;

    @Field("is_active")
    private Boolean isActive;

    @NotNull
    @Field("created_by")
    private String createdBy;

    @NotNull
    @Field("creation_date")
    private LocalDate creationDate;

    @DBRef
    @Field("addressType")
    private AddressTypes addressType;

    @DBRef
    @Field("state")
    private States state;

    @DBRef
    @Field("country")
    private Country country;

    @DBRef
    @Field("user")
    @JsonIgnoreProperties(value = "addresses", allowSetters = true)
    private Users user;

    @DBRef
    @Field("project")
    @JsonIgnoreProperties(value = "addresses", allowSetters = true)
    private Projects project;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress1() {
        return address1;
    }

    public Addresses address1(String address1) {
        this.address1 = address1;
        return this;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public Addresses address2(String address2) {
        this.address2 = address2;
        return this;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public Addresses city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getZipCode() {
        return zipCode;
    }

    public Addresses zipCode(Long zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public void setZipCode(Long zipCode) {
        this.zipCode = zipCode;
    }

    public Boolean isIsActive() {
        return isActive;
    }

    public Addresses isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Addresses createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Addresses creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public AddressTypes getAddressType() {
        return addressType;
    }

    public Addresses addressType(AddressTypes addressTypes) {
        this.addressType = addressTypes;
        return this;
    }

    public void setAddressType(AddressTypes addressTypes) {
        this.addressType = addressTypes;
    }

    public States getState() {
        return state;
    }

    public Addresses state(States states) {
        this.state = states;
        return this;
    }

    public void setState(States states) {
        this.state = states;
    }

    public Country getCountry() {
        return country;
    }

    public Addresses country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Users getUser() {
        return user;
    }

    public Addresses user(Users users) {
        this.user = users;
        return this;
    }

    public void setUser(Users users) {
        this.user = users;
    }

    public Projects getProject() {
        return project;
    }

    public Addresses project(Projects projects) {
        this.project = projects;
        return this;
    }

    public void setProject(Projects projects) {
        this.project = projects;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Addresses)) {
            return false;
        }
        return id != null && id.equals(((Addresses) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Addresses{" +
            "id=" + getId() +
            ", address1='" + getAddress1() + "'" +
            ", address2='" + getAddress2() + "'" +
            ", city='" + getCity() + "'" +
            ", zipCode=" + getZipCode() +
            ", isActive='" + isIsActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}
