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
 * A Tasks.
 */
@Document(collection = "tasks")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "tasks")
public class Tasks implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("title")
    private String title;

    @Field("start_date")
    private LocalDate startDate;

    @Field("due_date")
    private LocalDate dueDate;

    @Field("description")
    private String description;

    @Field("cost_impact")
    private Boolean costImpact;

    @Field("cost_impact_comment")
    private String costImpactComment;

    @Field("schedule_impact")
    private Boolean scheduleImpact;

    @Field("schedule_impact_comment")
    private String scheduleImpactComment;

    @NotNull
    @Field("created_by")
    private String createdBy;

    @NotNull
    @Field("creation_date")
    private LocalDate creationDate;

    @DBRef
    @Field("type")
    private TaskTypes type;

    @DBRef
    @Field("status")
    private TaskStatuses status;

    @DBRef
    @Field("location")
    private TaskLocations location;

    @DBRef
    @Field("stamp")
    private Stamps stamp;

    @DBRef
    @Field("list")
    private Lists list;

    @DBRef
    @Field("sheet")
    private Sheets sheet;

    @DBRef
    @Field("rootCauses")
    private RootCauses rootCauses;

    @DBRef
    @Field("assignedTo")
    private Set<Users> assignedTos = new HashSet<>();

    @DBRef
    @Field("monitors")
    private Set<Users> monitors = new HashSet<>();

    @DBRef
    @Field("images")
    private Set<TaskAttachmentImages> images = new HashSet<>();

    @DBRef
    @Field("othersAttachment")
    private Set<TaskAttachmentOthers> othersAttachments = new HashSet<>();

    @DBRef
    @Field("taskComments")
    private Set<TaskComments> taskComments = new HashSet<>();

    @DBRef
    @Field("project")
    @JsonIgnoreProperties(value = "tasks", allowSetters = true)
    private Projects project;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Tasks title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Tasks startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Tasks dueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public Tasks description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isCostImpact() {
        return costImpact;
    }

    public Tasks costImpact(Boolean costImpact) {
        this.costImpact = costImpact;
        return this;
    }

    public void setCostImpact(Boolean costImpact) {
        this.costImpact = costImpact;
    }

    public String getCostImpactComment() {
        return costImpactComment;
    }

    public Tasks costImpactComment(String costImpactComment) {
        this.costImpactComment = costImpactComment;
        return this;
    }

    public void setCostImpactComment(String costImpactComment) {
        this.costImpactComment = costImpactComment;
    }

    public Boolean isScheduleImpact() {
        return scheduleImpact;
    }

    public Tasks scheduleImpact(Boolean scheduleImpact) {
        this.scheduleImpact = scheduleImpact;
        return this;
    }

    public void setScheduleImpact(Boolean scheduleImpact) {
        this.scheduleImpact = scheduleImpact;
    }

    public String getScheduleImpactComment() {
        return scheduleImpactComment;
    }

    public Tasks scheduleImpactComment(String scheduleImpactComment) {
        this.scheduleImpactComment = scheduleImpactComment;
        return this;
    }

    public void setScheduleImpactComment(String scheduleImpactComment) {
        this.scheduleImpactComment = scheduleImpactComment;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Tasks createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Tasks creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public TaskTypes getType() {
        return type;
    }

    public Tasks type(TaskTypes taskTypes) {
        this.type = taskTypes;
        return this;
    }

    public void setType(TaskTypes taskTypes) {
        this.type = taskTypes;
    }

    public TaskStatuses getStatus() {
        return status;
    }

    public Tasks status(TaskStatuses taskStatuses) {
        this.status = taskStatuses;
        return this;
    }

    public void setStatus(TaskStatuses taskStatuses) {
        this.status = taskStatuses;
    }

    public TaskLocations getLocation() {
        return location;
    }

    public Tasks location(TaskLocations taskLocations) {
        this.location = taskLocations;
        return this;
    }

    public void setLocation(TaskLocations taskLocations) {
        this.location = taskLocations;
    }

    public Stamps getStamp() {
        return stamp;
    }

    public Tasks stamp(Stamps stamps) {
        this.stamp = stamps;
        return this;
    }

    public void setStamp(Stamps stamps) {
        this.stamp = stamps;
    }

    public Lists getList() {
        return list;
    }

    public Tasks list(Lists lists) {
        this.list = lists;
        return this;
    }

    public void setList(Lists lists) {
        this.list = lists;
    }

    public Sheets getSheet() {
        return sheet;
    }

    public Tasks sheet(Sheets sheets) {
        this.sheet = sheets;
        return this;
    }

    public void setSheet(Sheets sheets) {
        this.sheet = sheets;
    }

    public RootCauses getRootCauses() {
        return rootCauses;
    }

    public Tasks rootCauses(RootCauses rootCauses) {
        this.rootCauses = rootCauses;
        return this;
    }

    public void setRootCauses(RootCauses rootCauses) {
        this.rootCauses = rootCauses;
    }

    public Set<Users> getAssignedTos() {
        return assignedTos;
    }

    public Tasks assignedTos(Set<Users> users) {
        this.assignedTos = users;
        return this;
    }

    public Tasks addAssignedTo(Users users) {
        this.assignedTos.add(users);
        users.setTaskAssigned(this);
        return this;
    }

    public Tasks removeAssignedTo(Users users) {
        this.assignedTos.remove(users);
        users.setTaskAssigned(null);
        return this;
    }

    public void setAssignedTos(Set<Users> users) {
        this.assignedTos = users;
    }

    public Set<Users> getMonitors() {
        return monitors;
    }

    public Tasks monitors(Set<Users> users) {
        this.monitors = users;
        return this;
    }

    public Tasks addMonitors(Users users) {
        this.monitors.add(users);
        users.setTaskToMonitor(this);
        return this;
    }

    public Tasks removeMonitors(Users users) {
        this.monitors.remove(users);
        users.setTaskToMonitor(null);
        return this;
    }

    public void setMonitors(Set<Users> users) {
        this.monitors = users;
    }

    public Set<TaskAttachmentImages> getImages() {
        return images;
    }

    public Tasks images(Set<TaskAttachmentImages> taskAttachmentImages) {
        this.images = taskAttachmentImages;
        return this;
    }

    public Tasks addImages(TaskAttachmentImages taskAttachmentImages) {
        this.images.add(taskAttachmentImages);
        taskAttachmentImages.setTask(this);
        return this;
    }

    public Tasks removeImages(TaskAttachmentImages taskAttachmentImages) {
        this.images.remove(taskAttachmentImages);
        taskAttachmentImages.setTask(null);
        return this;
    }

    public void setImages(Set<TaskAttachmentImages> taskAttachmentImages) {
        this.images = taskAttachmentImages;
    }

    public Set<TaskAttachmentOthers> getOthersAttachments() {
        return othersAttachments;
    }

    public Tasks othersAttachments(Set<TaskAttachmentOthers> taskAttachmentOthers) {
        this.othersAttachments = taskAttachmentOthers;
        return this;
    }

    public Tasks addOthersAttachment(TaskAttachmentOthers taskAttachmentOthers) {
        this.othersAttachments.add(taskAttachmentOthers);
        taskAttachmentOthers.setTask(this);
        return this;
    }

    public Tasks removeOthersAttachment(TaskAttachmentOthers taskAttachmentOthers) {
        this.othersAttachments.remove(taskAttachmentOthers);
        taskAttachmentOthers.setTask(null);
        return this;
    }

    public void setOthersAttachments(Set<TaskAttachmentOthers> taskAttachmentOthers) {
        this.othersAttachments = taskAttachmentOthers;
    }

    public Set<TaskComments> getTaskComments() {
        return taskComments;
    }

    public Tasks taskComments(Set<TaskComments> taskComments) {
        this.taskComments = taskComments;
        return this;
    }

    public Tasks addTaskComments(TaskComments taskComments) {
        this.taskComments.add(taskComments);
        taskComments.setTask(this);
        return this;
    }

    public Tasks removeTaskComments(TaskComments taskComments) {
        this.taskComments.remove(taskComments);
        taskComments.setTask(null);
        return this;
    }

    public void setTaskComments(Set<TaskComments> taskComments) {
        this.taskComments = taskComments;
    }

    public Projects getProject() {
        return project;
    }

    public Tasks project(Projects projects) {
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
        if (!(o instanceof Tasks)) {
            return false;
        }
        return id != null && id.equals(((Tasks) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tasks{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", costImpact='" + isCostImpact() + "'" +
            ", costImpactComment='" + getCostImpactComment() + "'" +
            ", scheduleImpact='" + isScheduleImpact() + "'" +
            ", scheduleImpactComment='" + getScheduleImpactComment() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}
