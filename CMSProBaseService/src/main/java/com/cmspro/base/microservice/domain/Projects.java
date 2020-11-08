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
import java.util.HashSet;
import java.util.Set;

/**
 * A Projects.
 */
@Document(collection = "projects")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "projects")
public class Projects implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("code")
    private String code;

    @NotNull
    @Field("name")
    private String name;

    @Field("start_date")
    private LocalDate startDate;

    @Field("finish_date")
    private LocalDate finishDate;

    @Field("last_update")
    private LocalDate lastUpdate;

    @NotNull
    @Field("created_by")
    private String createdBy;

    @NotNull
    @Field("creation_date")
    private LocalDate creationDate;

    @DBRef
    @Field("address")
    private Set<Addresses> addresses = new HashSet<>();

    @DBRef
    @Field("sheets")
    private Set<Sheets> sheets = new HashSet<>();

    @DBRef
    @Field("teams")
    private Set<ProjectTeams> teams = new HashSet<>();

    @DBRef
    @Field("tasks")
    private Set<Tasks> tasks = new HashSet<>();

    @DBRef
    @Field("stamps")
    private Set<Stamps> stamps = new HashSet<>();

    @DBRef
    @Field("list")
    private Set<Lists> lists = new HashSet<>();

    @DBRef
    @Field("rfi")
    private Set<RFI> rfis = new HashSet<>();

    @DBRef
    @Field("account")
    @JsonIgnoreProperties(value = "projects", allowSetters = true)
    private Accounts account;

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

    public Projects code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Projects name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Projects startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public Projects finishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
        return this;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public Projects lastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
        return this;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Projects createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Projects creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Set<Addresses> getAddresses() {
        return addresses;
    }

    public Projects addresses(Set<Addresses> addresses) {
        this.addresses = addresses;
        return this;
    }

    public Projects addAddress(Addresses addresses) {
        this.addresses.add(addresses);
        addresses.setProject(this);
        return this;
    }

    public Projects removeAddress(Addresses addresses) {
        this.addresses.remove(addresses);
        addresses.setProject(null);
        return this;
    }

    public void setAddresses(Set<Addresses> addresses) {
        this.addresses = addresses;
    }

    public Set<Sheets> getSheets() {
        return sheets;
    }

    public Projects sheets(Set<Sheets> sheets) {
        this.sheets = sheets;
        return this;
    }

    public Projects addSheets(Sheets sheets) {
        this.sheets.add(sheets);
        sheets.setProject(this);
        return this;
    }

    public Projects removeSheets(Sheets sheets) {
        this.sheets.remove(sheets);
        sheets.setProject(null);
        return this;
    }

    public void setSheets(Set<Sheets> sheets) {
        this.sheets = sheets;
    }

    public Set<ProjectTeams> getTeams() {
        return teams;
    }

    public Projects teams(Set<ProjectTeams> projectTeams) {
        this.teams = projectTeams;
        return this;
    }

    public Projects addTeams(ProjectTeams projectTeams) {
        this.teams.add(projectTeams);
        projectTeams.setProject(this);
        return this;
    }

    public Projects removeTeams(ProjectTeams projectTeams) {
        this.teams.remove(projectTeams);
        projectTeams.setProject(null);
        return this;
    }

    public void setTeams(Set<ProjectTeams> projectTeams) {
        this.teams = projectTeams;
    }

    public Set<Tasks> getTasks() {
        return tasks;
    }

    public Projects tasks(Set<Tasks> tasks) {
        this.tasks = tasks;
        return this;
    }

    public Projects addTasks(Tasks tasks) {
        this.tasks.add(tasks);
        tasks.setProject(this);
        return this;
    }

    public Projects removeTasks(Tasks tasks) {
        this.tasks.remove(tasks);
        tasks.setProject(null);
        return this;
    }

    public void setTasks(Set<Tasks> tasks) {
        this.tasks = tasks;
    }

    public Set<Stamps> getStamps() {
        return stamps;
    }

    public Projects stamps(Set<Stamps> stamps) {
        this.stamps = stamps;
        return this;
    }

    public Projects addStamps(Stamps stamps) {
        this.stamps.add(stamps);
        stamps.setProject(this);
        return this;
    }

    public Projects removeStamps(Stamps stamps) {
        this.stamps.remove(stamps);
        stamps.setProject(null);
        return this;
    }

    public void setStamps(Set<Stamps> stamps) {
        this.stamps = stamps;
    }

    public Set<Lists> getLists() {
        return lists;
    }

    public Projects lists(Set<Lists> lists) {
        this.lists = lists;
        return this;
    }

    public Projects addList(Lists lists) {
        this.lists.add(lists);
        lists.setProject(this);
        return this;
    }

    public Projects removeList(Lists lists) {
        this.lists.remove(lists);
        lists.setProject(null);
        return this;
    }

    public void setLists(Set<Lists> lists) {
        this.lists = lists;
    }

    public Set<RFI> getRfis() {
        return rfis;
    }

    public Projects rfis(Set<RFI> rFIS) {
        this.rfis = rFIS;
        return this;
    }

    public Projects addRfi(RFI rFI) {
        this.rfis.add(rFI);
        rFI.setProject(this);
        return this;
    }

    public Projects removeRfi(RFI rFI) {
        this.rfis.remove(rFI);
        rFI.setProject(null);
        return this;
    }

    public void setRfis(Set<RFI> rFIS) {
        this.rfis = rFIS;
    }

    public Accounts getAccount() {
        return account;
    }

    public Projects account(Accounts accounts) {
        this.account = accounts;
        return this;
    }

    public void setAccount(Accounts accounts) {
        this.account = accounts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Projects)) {
            return false;
        }
        return id != null && id.equals(((Projects) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Projects{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", finishDate='" + getFinishDate() + "'" +
            ", lastUpdate='" + getLastUpdate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}
