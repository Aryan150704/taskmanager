package com.taskmanager.taskmanager.dto;

import com.taskmanager.taskmanager.entity.ProjectMember;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddMemberRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email")
    private String email;

    @NotNull(message = "Role is required")
    private ProjectMember.Role role; // ADMIN or MEMBER

    public AddMemberRequest() {}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public ProjectMember.Role getRole() { return role; }
    public void setRole(ProjectMember.Role role) { this.role = role; }
}
