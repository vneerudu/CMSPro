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
 * A Users.
 */
@Document(collection = "users")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "users")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("first_name")
    private String firstName;

    @NotNull
    @Field("last_name")
    private String lastName;

    @Field("full_name")
    private String fullName;

    @Field("prefix")
    private String prefix;

    @Field("email_address")
    private String emailAddress;

    @Field("phone_number")
    private String phoneNumber;

    @Field("title")
    private String title;

    @Field("company")
    private String company;

    @NotNull
    @Field("track_location")
    private Boolean trackLocation;

    @DBRef
    @Field("userGroup")
    private Set<UserGroups> userGroups = new HashSet<>();

    @DBRef
    @Field("address")
    private Set<Addresses> addresses = new HashSet<>();

    @DBRef
    @Field("account")
    @JsonIgnoreProperties(value = "users", allowSetters = true)
    private Accounts account;

    @DBRef
    @Field("team")
    @JsonIgnoreProperties(value = "users", allowSetters = true)
    private ProjectTeams team;

    @DBRef
    @Field("taskAssigned")
    @JsonIgnoreProperties(value = "assignedTos", allowSetters = true)
    private Tasks taskAssigned;

    @DBRef
    @Field("taskToMonitor")
    @JsonIgnoreProperties(value = "monitors", allowSetters = true)
    private Tasks taskToMonitor;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Users firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Users lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public Users fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPrefix() {
        return prefix;
    }

    public Users prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Users emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Users phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTitle() {
        return title;
    }

    public Users title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public Users company(String company) {
        this.company = company;
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Boolean isTrackLocation() {
        return trackLocation;
    }

    public Users trackLocation(Boolean trackLocation) {
        this.trackLocation = trackLocation;
        return this;
    }

    public void setTrackLocation(Boolean trackLocation) {
        this.trackLocation = trackLocation;
    }

    public Set<UserGroups> getUserGroups() {
        return userGroups;
    }

    public Users userGroups(Set<UserGroups> userGroups) {
        this.userGroups = userGroups;
        return this;
    }

    public Users addUserGroup(UserGroups userGroups) {
        this.userGroups.add(userGroups);
        userGroups.setUsers(this);
        return this;
    }

    public Users removeUserGroup(UserGroups userGroups) {
        this.userGroups.remove(userGroups);
        userGroups.setUsers(null);
        return this;
    }

    public void setUserGroups(Set<UserGroups> userGroups) {
        this.userGroups = userGroups;
    }

    public Set<Addresses> getAddresses() {
        return addresses;
    }

    public Users addresses(Set<Addresses> addresses) {
        this.addresses = addresses;
        return this;
    }

    public Users addAddress(Addresses addresses) {
        this.addresses.add(addresses);
        addresses.setUser(this);
        return this;
    }

    public Users removeAddress(Addresses addresses) {
        this.addresses.remove(addresses);
        addresses.setUser(null);
        return this;
    }

    public void setAddresses(Set<Addresses> addresses) {
        this.addresses = addresses;
    }

    public Accounts getAccount() {
        return account;
    }

    public Users account(Accounts accounts) {
        this.account = accounts;
        return this;
    }

    public void setAccount(Accounts accounts) {
        this.account = accounts;
    }

    public ProjectTeams getTeam() {
        return team;
    }

    public Users team(ProjectTeams projectTeams) {
        this.team = projectTeams;
        return this;
    }

    public void setTeam(ProjectTeams projectTeams) {
        this.team = projectTeams;
    }

    public Tasks getTaskAssigned() {
        return taskAssigned;
    }

    public Users taskAssigned(Tasks tasks) {
        this.taskAssigned = tasks;
        return this;
    }

    public void setTaskAssigned(Tasks tasks) {
        this.taskAssigned = tasks;
    }

    public Tasks getTaskToMonitor() {
        return taskToMonitor;
    }

    public Users taskToMonitor(Tasks tasks) {
        this.taskToMonitor = tasks;
        return this;
    }

    public void setTaskToMonitor(Tasks tasks) {
        this.taskToMonitor = tasks;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Users)) {
            return false;
        }
        return id != null && id.equals(((Users) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Users{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", prefix='" + getPrefix() + "'" +
            ", emailAddress='" + getEmailAddress() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", title='" + getTitle() + "'" +
            ", company='" + getCompany() + "'" +
            ", trackLocation='" + isTrackLocation() + "'" +
            "}";
    }
}
