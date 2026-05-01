package com.taskmanager.taskmanager.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "project_members",
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "project_id"}))
public class ProjectMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public enum Role {
        ADMIN, MEMBER
    }

    // ─── Required by JPA ───────────────────────────────
    public ProjectMember() {}

    // ─── Getters & Setters ──────────────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    // ─── Builder ────────────────────────────────────────
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private User user;
        private Project project;
        private Role role;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder user(User user) { this.user = user; return this; }
        public Builder project(Project project) { this.project = project; return this; }
        public Builder role(Role role) { this.role = role; return this; }

        public ProjectMember build() {
            ProjectMember m = new ProjectMember();
            m.id = this.id;
            m.user = this.user;
            m.project = this.project;
            m.role = this.role;
            return m;
        }
    }

    @Override
    public String toString() {
        return "ProjectMember{id=" + id + ", role=" + role + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectMember)) return false;
        ProjectMember pm = (ProjectMember) o;
        return id != null && id.equals(pm.id);
    }

    @Override
    public int hashCode() { return getClass().hashCode(); }
}
