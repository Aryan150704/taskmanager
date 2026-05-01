package com.taskmanager.taskmanager.dto;

import com.taskmanager.taskmanager.entity.Task;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private Task.Status status;
    private LocalDate dueDate;
    private boolean overdue;
    private Long projectId;
    private String projectName;
    private Long assigneeId;
    private String assigneeName;
    private String createdByName;
    private LocalDateTime createdAt;

    public TaskResponse() {}

    // ─── Getters & Setters ──────────────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Task.Status getStatus() { return status; }
    public void setStatus(Task.Status status) { this.status = status; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public boolean isOverdue() { return overdue; }
    public void setOverdue(boolean overdue) { this.overdue = overdue; }

    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }

    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }

    public Long getAssigneeId() { return assigneeId; }
    public void setAssigneeId(Long assigneeId) { this.assigneeId = assigneeId; }

    public String getAssigneeName() { return assigneeName; }
    public void setAssigneeName(String assigneeName) { this.assigneeName = assigneeName; }

    public String getCreatedByName() { return createdByName; }
    public void setCreatedByName(String createdByName) { this.createdByName = createdByName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // ─── Builder ────────────────────────────────────────
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String title;
        private String description;
        private Task.Status status;
        private LocalDate dueDate;
        private boolean overdue;
        private Long projectId;
        private String projectName;
        private Long assigneeId;
        private String assigneeName;
        private String createdByName;
        private LocalDateTime createdAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder status(Task.Status status) { this.status = status; return this; }
        public Builder dueDate(LocalDate dueDate) { this.dueDate = dueDate; return this; }
        public Builder overdue(boolean overdue) { this.overdue = overdue; return this; }
        public Builder projectId(Long projectId) { this.projectId = projectId; return this; }
        public Builder projectName(String projectName) { this.projectName = projectName; return this; }
        public Builder assigneeId(Long assigneeId) { this.assigneeId = assigneeId; return this; }
        public Builder assigneeName(String assigneeName) { this.assigneeName = assigneeName; return this; }
        public Builder createdByName(String createdByName) { this.createdByName = createdByName; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public TaskResponse build() {
            TaskResponse r = new TaskResponse();
            r.id = this.id;
            r.title = this.title;
            r.description = this.description;
            r.status = this.status;
            r.dueDate = this.dueDate;
            r.overdue = this.overdue;
            r.projectId = this.projectId;
            r.projectName = this.projectName;
            r.assigneeId = this.assigneeId;
            r.assigneeName = this.assigneeName;
            r.createdByName = this.createdByName;
            r.createdAt = this.createdAt;
            return r;
        }
    }
}
