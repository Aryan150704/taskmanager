package com.taskmanager.taskmanager.dto;

public class MemberResponse {
    private Long userId;
    private String name;
    private String email;
    private String role;

    public MemberResponse() {}
    public MemberResponse(Long userId, String name, String email, String role) {
        this.userId = userId; this.name = name; this.email = email; this.role = role;
    }

    public Long getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setRole(String role) { this.role = role; }
}
