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
 * A RFI.
 */
@Document(collection = "rfi")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "rfi")
public class RFI implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("title")
    private String title;

    @NotNull
    @Field("question")
    private String question;

    @Field("answer")
    private String answer;

    @Field("sent_date")
    private LocalDate sentDate;

    @Field("due_date")
    private LocalDate dueDate;

    @Field("locked")
    private Boolean locked;

    @Field("locked_by")
    private String lockedBy;

    @DBRef
    @Field("status")
    private RFIStatuses status;

    @DBRef
    @Field("taskImages")
    private Set<TaskAttachmentImages> taskImages = new HashSet<>();

    @DBRef
    @Field("taskAttachment")
    private Set<TaskAttachmentOthers> taskAttachments = new HashSet<>();

    @DBRef
    @Field("sheetAttachment")
    private Set<AttachmentOthers> sheetAttachments = new HashSet<>();

    @DBRef
    @Field("sheetImages")
    private Set<AttachmentImages> sheetImages = new HashSet<>();

    @DBRef
    @Field("rfiComments")
    private Set<RFIComments> rfiComments = new HashSet<>();

    @DBRef
    @Field("timeLine")
    private Set<RFITimeLine> timeLines = new HashSet<>();

    @DBRef
    @Field("project")
    @JsonIgnoreProperties(value = "rfis", allowSetters = true)
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

    public RFI title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestion() {
        return question;
    }

    public RFI question(String question) {
        this.question = question;
        return this;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public RFI answer(String answer) {
        this.answer = answer;
        return this;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public LocalDate getSentDate() {
        return sentDate;
    }

    public RFI sentDate(LocalDate sentDate) {
        this.sentDate = sentDate;
        return this;
    }

    public void setSentDate(LocalDate sentDate) {
        this.sentDate = sentDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public RFI dueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean isLocked() {
        return locked;
    }

    public RFI locked(Boolean locked) {
        this.locked = locked;
        return this;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getLockedBy() {
        return lockedBy;
    }

    public RFI lockedBy(String lockedBy) {
        this.lockedBy = lockedBy;
        return this;
    }

    public void setLockedBy(String lockedBy) {
        this.lockedBy = lockedBy;
    }

    public RFIStatuses getStatus() {
        return status;
    }

    public RFI status(RFIStatuses rFIStatuses) {
        this.status = rFIStatuses;
        return this;
    }

    public void setStatus(RFIStatuses rFIStatuses) {
        this.status = rFIStatuses;
    }

    public Set<TaskAttachmentImages> getTaskImages() {
        return taskImages;
    }

    public RFI taskImages(Set<TaskAttachmentImages> taskAttachmentImages) {
        this.taskImages = taskAttachmentImages;
        return this;
    }

    public RFI addTaskImages(TaskAttachmentImages taskAttachmentImages) {
        this.taskImages.add(taskAttachmentImages);
        taskAttachmentImages.setRfi(this);
        return this;
    }

    public RFI removeTaskImages(TaskAttachmentImages taskAttachmentImages) {
        this.taskImages.remove(taskAttachmentImages);
        taskAttachmentImages.setRfi(null);
        return this;
    }

    public void setTaskImages(Set<TaskAttachmentImages> taskAttachmentImages) {
        this.taskImages = taskAttachmentImages;
    }

    public Set<TaskAttachmentOthers> getTaskAttachments() {
        return taskAttachments;
    }

    public RFI taskAttachments(Set<TaskAttachmentOthers> taskAttachmentOthers) {
        this.taskAttachments = taskAttachmentOthers;
        return this;
    }

    public RFI addTaskAttachment(TaskAttachmentOthers taskAttachmentOthers) {
        this.taskAttachments.add(taskAttachmentOthers);
        taskAttachmentOthers.setRfi(this);
        return this;
    }

    public RFI removeTaskAttachment(TaskAttachmentOthers taskAttachmentOthers) {
        this.taskAttachments.remove(taskAttachmentOthers);
        taskAttachmentOthers.setRfi(null);
        return this;
    }

    public void setTaskAttachments(Set<TaskAttachmentOthers> taskAttachmentOthers) {
        this.taskAttachments = taskAttachmentOthers;
    }

    public Set<AttachmentOthers> getSheetAttachments() {
        return sheetAttachments;
    }

    public RFI sheetAttachments(Set<AttachmentOthers> attachmentOthers) {
        this.sheetAttachments = attachmentOthers;
        return this;
    }

    public RFI addSheetAttachment(AttachmentOthers attachmentOthers) {
        this.sheetAttachments.add(attachmentOthers);
        attachmentOthers.setRfi(this);
        return this;
    }

    public RFI removeSheetAttachment(AttachmentOthers attachmentOthers) {
        this.sheetAttachments.remove(attachmentOthers);
        attachmentOthers.setRfi(null);
        return this;
    }

    public void setSheetAttachments(Set<AttachmentOthers> attachmentOthers) {
        this.sheetAttachments = attachmentOthers;
    }

    public Set<AttachmentImages> getSheetImages() {
        return sheetImages;
    }

    public RFI sheetImages(Set<AttachmentImages> attachmentImages) {
        this.sheetImages = attachmentImages;
        return this;
    }

    public RFI addSheetImages(AttachmentImages attachmentImages) {
        this.sheetImages.add(attachmentImages);
        attachmentImages.setRfi(this);
        return this;
    }

    public RFI removeSheetImages(AttachmentImages attachmentImages) {
        this.sheetImages.remove(attachmentImages);
        attachmentImages.setRfi(null);
        return this;
    }

    public void setSheetImages(Set<AttachmentImages> attachmentImages) {
        this.sheetImages = attachmentImages;
    }

    public Set<RFIComments> getRfiComments() {
        return rfiComments;
    }

    public RFI rfiComments(Set<RFIComments> rFIComments) {
        this.rfiComments = rFIComments;
        return this;
    }

    public RFI addRfiComments(RFIComments rFIComments) {
        this.rfiComments.add(rFIComments);
        rFIComments.setRfi(this);
        return this;
    }

    public RFI removeRfiComments(RFIComments rFIComments) {
        this.rfiComments.remove(rFIComments);
        rFIComments.setRfi(null);
        return this;
    }

    public void setRfiComments(Set<RFIComments> rFIComments) {
        this.rfiComments = rFIComments;
    }

    public Set<RFITimeLine> getTimeLines() {
        return timeLines;
    }

    public RFI timeLines(Set<RFITimeLine> rFITimeLines) {
        this.timeLines = rFITimeLines;
        return this;
    }

    public RFI addTimeLine(RFITimeLine rFITimeLine) {
        this.timeLines.add(rFITimeLine);
        rFITimeLine.setRfi(this);
        return this;
    }

    public RFI removeTimeLine(RFITimeLine rFITimeLine) {
        this.timeLines.remove(rFITimeLine);
        rFITimeLine.setRfi(null);
        return this;
    }

    public void setTimeLines(Set<RFITimeLine> rFITimeLines) {
        this.timeLines = rFITimeLines;
    }

    public Projects getProject() {
        return project;
    }

    public RFI project(Projects projects) {
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
        if (!(o instanceof RFI)) {
            return false;
        }
        return id != null && id.equals(((RFI) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RFI{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", question='" + getQuestion() + "'" +
            ", answer='" + getAnswer() + "'" +
            ", sentDate='" + getSentDate() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", locked='" + isLocked() + "'" +
            ", lockedBy='" + getLockedBy() + "'" +
            "}";
    }
}
