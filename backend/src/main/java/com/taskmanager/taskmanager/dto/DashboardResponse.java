package com.taskmanager.taskmanager.dto;

import java.util.List;

public class DashboardResponse {

    private int totalProjects;
    private int totalTasks;
    private int todoCount;
    private int inProgressCount;
    private int doneCount;
    private int overdueCount;
    private List<TaskResponse> myTasks;
    private List<TaskResponse> overdueTasks;
    private List<ProjectResponse> myProjects;

    public DashboardResponse() {}

    // ─── Getters & Setters ──────────────────────────────
    public int getTotalProjects() { return totalProjects; }
    public void setTotalProjects(int totalProjects) { this.totalProjects = totalProjects; }

    public int getTotalTasks() { return totalTasks; }
    public void setTotalTasks(int totalTasks) { this.totalTasks = totalTasks; }

    public int getTodoCount() { return todoCount; }
    public void setTodoCount(int todoCount) { this.todoCount = todoCount; }

    public int getInProgressCount() { return inProgressCount; }
    public void setInProgressCount(int inProgressCount) { this.inProgressCount = inProgressCount; }

    public int getDoneCount() { return doneCount; }
    public void setDoneCount(int doneCount) { this.doneCount = doneCount; }

    public int getOverdueCount() { return overdueCount; }
    public void setOverdueCount(int overdueCount) { this.overdueCount = overdueCount; }

    public List<TaskResponse> getMyTasks() { return myTasks; }
    public void setMyTasks(List<TaskResponse> myTasks) { this.myTasks = myTasks; }

    public List<TaskResponse> getOverdueTasks() { return overdueTasks; }
    public void setOverdueTasks(List<TaskResponse> overdueTasks) { this.overdueTasks = overdueTasks; }

    public List<ProjectResponse> getMyProjects() { return myProjects; }
    public void setMyProjects(List<ProjectResponse> myProjects) { this.myProjects = myProjects; }

    // ─── Builder ────────────────────────────────────────
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private int totalProjects;
        private int totalTasks;
        private int todoCount;
        private int inProgressCount;
        private int doneCount;
        private int overdueCount;
        private List<TaskResponse> myTasks;
        private List<TaskResponse> overdueTasks;
        private List<ProjectResponse> myProjects;

        public Builder totalProjects(int v) { this.totalProjects = v; return this; }
        public Builder totalTasks(int v) { this.totalTasks = v; return this; }
        public Builder todoCount(int v) { this.todoCount = v; return this; }
        public Builder inProgressCount(int v) { this.inProgressCount = v; return this; }
        public Builder doneCount(int v) { this.doneCount = v; return this; }
        public Builder overdueCount(int v) { this.overdueCount = v; return this; }
        public Builder myTasks(List<TaskResponse> v) { this.myTasks = v; return this; }
        public Builder overdueTasks(List<TaskResponse> v) { this.overdueTasks = v; return this; }
        public Builder myProjects(List<ProjectResponse> v) { this.myProjects = v; return this; }

        public DashboardResponse build() {
            DashboardResponse r = new DashboardResponse();
            r.totalProjects = this.totalProjects;
            r.totalTasks = this.totalTasks;
            r.todoCount = this.todoCount;
            r.inProgressCount = this.inProgressCount;
            r.doneCount = this.doneCount;
            r.overdueCount = this.overdueCount;
            r.myTasks = this.myTasks;
            r.overdueTasks = this.overdueTasks;
            r.myProjects = this.myProjects;
            return r;
        }
    }
}
