package com.taskmanager.taskmanager.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectMember> members = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    @Column(updatable = false)
    private LocalDateTime createdAt;

    // ─── Required by JPA ───────────────────────────────
    public Project() {}

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    // ─── Getters & Setters ──────────────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    public List<ProjectMember> getMembers() { return members; }
    public void setMembers(List<ProjectMember> members) { this.members = members; }

    public List<Task> getTasks() { return tasks; }
    public void setTasks(List<Task> tasks) { this.tasks = tasks; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // ─── Builder ────────────────────────────────────────
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String name;
        private String description;
        private User owner;
        private List<ProjectMember> members = new ArrayList<>();
        private List<Task> tasks = new ArrayList<>();
        private LocalDateTime createdAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder owner(User owner) { this.owner = owner; return this; }
        public Builder members(List<ProjectMember> members) { this.members = members; return this; }
        public Builder tasks(List<Task> tasks) { this.tasks = tasks; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public Project build() {
            Project p = new Project();
            p.id = this.id;
            p.name = this.name;
            p.description = this.description;
            p.owner = this.owner;
            p.members = this.members != null ? this.members : new ArrayList<>();
            p.tasks = this.tasks != null ? this.tasks : new ArrayList<>();
            p.createdAt = this.createdAt;
            return p;
        }
    }

    @Override
    public String toString() {
        return "Project{id=" + id + ", name='" + name + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;
        Project p = (Project) o;
        return id != null && id.equals(p.id);
    }

    @Override
    public int hashCode() { return getClass().hashCode(); }
}
