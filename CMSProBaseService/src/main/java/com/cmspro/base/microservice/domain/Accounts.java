package com.cmspro.base.microservice.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Accounts.
 */
@Document(collection = "accounts")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "accounts")
public class Accounts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("account_number")
    private Long accountNumber;

    @NotNull
    @Field("first_name")
    private String firstName;

    @NotNull
    @Field("last_name")
    private String lastName;

    @NotNull
    @Field("email_address")
    private String emailAddress;

    @NotNull
    @Field("phone_number")
    private String phoneNumber;

    @NotNull
    @Field("creation_date")
    private LocalDate creationDate;

    @NotNull
    @Field("created_by")
    private String createdBy;

    @DBRef
    @Field("language")
    private Languages language;

    @DBRef
    @Field("logo")
    private Logos logo;

    @DBRef
    @Field("status")
    private AccountStatuses status;

    @DBRef
    @Field("users")
    private Set<Users> users = new HashSet<>();

    @DBRef
    @Field("groups")
    private Set<UserGroups> groups = new HashSet<>();

    @DBRef
    @Field("project")
    private Set<Projects> projects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public Accounts accountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public Accounts firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Accounts lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Accounts emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Accounts phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Accounts creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Accounts createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Languages getLanguage() {
        return language;
    }

    public Accounts language(Languages languages) {
        this.language = languages;
        return this;
    }

    public void setLanguage(Languages languages) {
        this.language = languages;
    }

    public Logos getLogo() {
        return logo;
    }

    public Accounts logo(Logos logos) {
        this.logo = logos;
        return this;
    }

    public void setLogo(Logos logos) {
        this.logo = logos;
    }

    public AccountStatuses getStatus() {
        return status;
    }

    public Accounts status(AccountStatuses accountStatuses) {
        this.status = accountStatuses;
        return this;
    }

    public void setStatus(AccountStatuses accountStatuses) {
        this.status = accountStatuses;
    }

    public Set<Users> getUsers() {
        return users;
    }

    public Accounts users(Set<Users> users) {
        this.users = users;
        return this;
    }

    public Accounts addUsers(Users users) {
        this.users.add(users);
        users.setAccount(this);
        return this;
    }

    public Accounts removeUsers(Users users) {
        this.users.remove(users);
        users.setAccount(null);
        return this;
    }

    public void setUsers(Set<Users> users) {
        this.users = users;
    }

    public Set<UserGroups> getGroups() {
        return groups;
    }

    public Accounts groups(Set<UserGroups> userGroups) {
        this.groups = userGroups;
        return this;
    }

    public Accounts addGroups(UserGroups userGroups) {
        this.groups.add(userGroups);
        userGroups.setAccount(this);
        return this;
    }

    public Accounts removeGroups(UserGroups userGroups) {
        this.groups.remove(userGroups);
        userGroups.setAccount(null);
        return this;
    }

    public void setGroups(Set<UserGroups> userGroups) {
        this.groups = userGroups;
    }

    public Set<Projects> getProjects() {
        return projects;
    }

    public Accounts projects(Set<Projects> projects) {
        this.projects = projects;
        return this;
    }

    public Accounts addProject(Projects projects) {
        this.projects.add(projects);
        projects.setAccount(this);
        return this;
    }

    public Accounts removeProject(Projects projects) {
        this.projects.remove(projects);
        projects.setAccount(null);
        return this;
    }

    public void setProjects(Set<Projects> projects) {
        this.projects = projects;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Accounts)) {
            return false;
        }
        return id != null && id.equals(((Accounts) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Accounts{" +
            "id=" + getId() +
            ", accountNumber=" + getAccountNumber() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", emailAddress='" + getEmailAddress() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
