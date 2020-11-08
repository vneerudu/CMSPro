package com.cmspro.base.microservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A Sheets.
 */
@Document(collection = "sheets")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "sheets")
public class Sheets implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("number")
    private Long number;

    @Field("title")
    private String title;

    @NotNull
    @Field("version")
    private String version;

    @NotNull
    @Field("created_by")
    private String createdBy;

    @NotNull
    @Field("creation_date")
    private LocalDate creationDate;

    @DBRef
    @Field("documents")
    private Documents documents;

    @DBRef
    @Field("tags")
    private Set<SheetTags> tags = new HashSet<>();

    @DBRef
    @Field("history")
    private Set<SheetHistory> histories = new HashSet<>();

    @DBRef
    @Field("attachment")
    private Set<Attachments> attachments = new HashSet<>();

    @DBRef
    @Field("comment")
    private Set<SheetComments> comments = new HashSet<>();

    @DBRef
    @Field("task")
    @com.fasterxml.jackson.annotation.JsonBackReference
    private Tasks task;

    @DBRef
    @Field("project")
    @JsonIgnoreProperties(value = "sheets", allowSetters = true)
    private Projects project;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public Sheets number(Long number) {
        this.number = number;
        return this;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public Sheets title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return version;
    }

    public Sheets version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Sheets createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Sheets creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Documents getDocuments() {
        return documents;
    }

    public Sheets documents(Documents documents) {
        this.documents = documents;
        return this;
    }

    public void setDocuments(Documents documents) {
        this.documents = documents;
    }

    public Set<SheetTags> getTags() {
        return tags;
    }

    public Sheets tags(Set<SheetTags> sheetTags) {
        this.tags = sheetTags;
        return this;
    }

    public Sheets addTags(SheetTags sheetTags) {
        this.tags.add(sheetTags);
        sheetTags.setSheets(this);
        return this;
    }

    public Sheets removeTags(SheetTags sheetTags) {
        this.tags.remove(sheetTags);
        sheetTags.setSheets(null);
        return this;
    }

    public void setTags(Set<SheetTags> sheetTags) {
        this.tags = sheetTags;
    }

    public Set<SheetHistory> getHistories() {
        return histories;
    }

    public Sheets histories(Set<SheetHistory> sheetHistories) {
        this.histories = sheetHistories;
        return this;
    }

    public Sheets addHistory(SheetHistory sheetHistory) {
        this.histories.add(sheetHistory);
        sheetHistory.setSheet(this);
        return this;
    }

    public Sheets removeHistory(SheetHistory sheetHistory) {
        this.histories.remove(sheetHistory);
        sheetHistory.setSheet(null);
        return this;
    }

    public void setHistories(Set<SheetHistory> sheetHistories) {
        this.histories = sheetHistories;
    }

    public Set<Attachments> getAttachments() {
        return attachments;
    }

    public Sheets attachments(Set<Attachments> attachments) {
        this.attachments = attachments;
        return this;
    }

    public Sheets addAttachment(Attachments attachments) {
        this.attachments.add(attachments);
        attachments.setSheet(this);
        return this;
    }

    public Sheets removeAttachment(Attachments attachments) {
        this.attachments.remove(attachments);
        attachments.setSheet(null);
        return this;
    }

    public void setAttachments(Set<Attachments> attachments) {
        this.attachments = attachments;
    }

    public Set<SheetComments> getComments() {
        return comments;
    }

    public Sheets comments(Set<SheetComments> sheetComments) {
        this.comments = sheetComments;
        return this;
    }

    public Sheets addComment(SheetComments sheetComments) {
        this.comments.add(sheetComments);
        sheetComments.setSheet(this);
        return this;
    }

    public Sheets removeComment(SheetComments sheetComments) {
        this.comments.remove(sheetComments);
        sheetComments.setSheet(null);
        return this;
    }

    public void setComments(Set<SheetComments> sheetComments) {
        this.comments = sheetComments;
    }

    public Tasks getTask() {
        return task;
    }

    public Sheets task(Tasks tasks) {
        this.task = tasks;
        return this;
    }

    public void setTask(Tasks tasks) {
        this.task = tasks;
    }

    public Projects getProject() {
        return project;
    }

    public Sheets project(Projects projects) {
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
        if (!(o instanceof Sheets)) {
            return false;
        }
        return id != null && id.equals(((Sheets) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sheets{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", title='" + getTitle() + "'" +
            ", version='" + getVersion() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}
