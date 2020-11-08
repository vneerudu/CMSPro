package com.cmspro.base.microservice.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.cmspro.base.microservice.domain.Users} entity.
 */
public class UsersDTO implements Serializable {
    
    private String id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String fullName;

    private String prefix;

    private String emailAddress;

    private String phoneNumber;

    private String title;

    private String company;

    @NotNull
    private Boolean trackLocation;


    private String accountId;

    private String accountAccountNumber;

    private String teamId;

    private String teamName;

    private String taskAssignedId;

    private String taskAssignedTitle;

    private String taskToMonitorId;

    private String taskToMonitorTitle;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Boolean isTrackLocation() {
        return trackLocation;
    }

    public void setTrackLocation(Boolean trackLocation) {
        this.trackLocation = trackLocation;
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

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String projectTeamsId) {
        this.teamId = projectTeamsId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String projectTeamsName) {
        this.teamName = projectTeamsName;
    }

    public String getTaskAssignedId() {
        return taskAssignedId;
    }

    public void setTaskAssignedId(String tasksId) {
        this.taskAssignedId = tasksId;
    }

    public String getTaskAssignedTitle() {
        return taskAssignedTitle;
    }

    public void setTaskAssignedTitle(String tasksTitle) {
        this.taskAssignedTitle = tasksTitle;
    }

    public String getTaskToMonitorId() {
        return taskToMonitorId;
    }

    public void setTaskToMonitorId(String tasksId) {
        this.taskToMonitorId = tasksId;
    }

    public String getTaskToMonitorTitle() {
        return taskToMonitorTitle;
    }

    public void setTaskToMonitorTitle(String tasksTitle) {
        this.taskToMonitorTitle = tasksTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UsersDTO)) {
            return false;
        }

        return id != null && id.equals(((UsersDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UsersDTO{" +
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
            ", accountId='" + getAccountId() + "'" +
            ", accountAccountNumber='" + getAccountAccountNumber() + "'" +
            ", teamId='" + getTeamId() + "'" +
            ", teamName='" + getTeamName() + "'" +
            ", taskAssignedId='" + getTaskAssignedId() + "'" +
            ", taskAssignedTitle='" + getTaskAssignedTitle() + "'" +
            ", taskToMonitorId='" + getTaskToMonitorId() + "'" +
            ", taskToMonitorTitle='" + getTaskToMonitorTitle() + "'" +
            "}";
    }
}
