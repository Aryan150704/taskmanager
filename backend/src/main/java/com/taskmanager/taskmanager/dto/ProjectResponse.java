package com.taskmanager.taskmanager.dto;

import java.time.LocalDateTime;

public class ProjectResponse {

    private Long id;
    private String name;
    private String description;
    private String ownerName;
    private String ownerEmail;
    private int memberCount;
    private int taskCount;
    private LocalDateTime createdAt;
    private String currentUserRole;

    public ProjectResponse() {}

    // ─── Getters & Setters ──────────────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public String getOwnerEmail() { return ownerEmail; }
    public void setOwnerEmail(String ownerEmail) { this.ownerEmail = ownerEmail; }

    public int getMemberCount() { return memberCount; }
    public void setMemberCount(int memberCount) { this.memberCount = memberCount; }

    public int getTaskCount() { return taskCount; }
    public void setTaskCount(int taskCount) { this.taskCount = taskCount; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getCurrentUserRole() { return currentUserRole; }
    public void setCurrentUserRole(String currentUserRole) { this.currentUserRole = currentUserRole; }

    // ─── Builder ────────────────────────────────────────
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String name;
        private String description;
        private String ownerName;
        private String ownerEmail;
        private int memberCount;
        private int taskCount;
        private LocalDateTime createdAt;
        private String currentUserRole;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder ownerName(String ownerName) { this.ownerName = ownerName; return this; }
        public Builder ownerEmail(String ownerEmail) { this.ownerEmail = ownerEmail; return this; }
        public Builder memberCount(int memberCount) { this.memberCount = memberCount; return this; }
        public Builder taskCount(int taskCount) { this.taskCount = taskCount; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder currentUserRole(String currentUserRole) { this.currentUserRole = currentUserRole; return this; }

        public ProjectResponse build() {
            ProjectResponse r = new ProjectResponse();
            r.id = this.id;
            r.name = this.name;
            r.description = this.description;
            r.ownerName = this.ownerName;
            r.ownerEmail = this.ownerEmail;
            r.memberCount = this.memberCount;
            r.taskCount = this.taskCount;
            r.createdAt = this.createdAt;
            r.currentUserRole = this.currentUserRole;
            return r;
        }
    }
}
