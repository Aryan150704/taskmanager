package com.taskmanager.taskmanager.controller;

import com.taskmanager.taskmanager.dto.AddMemberRequest;
import com.taskmanager.taskmanager.dto.ProjectRequest;
import com.taskmanager.taskmanager.dto.ProjectResponse;
import com.taskmanager.taskmanager.repository.UserRepository;
import com.taskmanager.taskmanager.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Project API (all routes require JWT token)
 *
 * GET    /api/projects                      → Get my projects
 * POST   /api/projects                      → Create a project
 * GET    /api/projects/{id}                 → Get one project
 * PUT    /api/projects/{id}                 → Update project (ADMIN)
 * DELETE /api/projects/{id}                 → Delete project (OWNER)
 * POST   /api/projects/{id}/members         → Add member (ADMIN)
 * DELETE /api/projects/{id}/members/{uid}   → Remove member (ADMIN)
 */
@RestController
@RequestMapping("/api/projects")
public class ProjectController extends BaseController {

    private final ProjectService projectService;

    public ProjectController(UserRepository userRepository, ProjectService projectService) {
        super(userRepository);
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getMyProjects() {
        return ResponseEntity.ok(projectService.getMyProjects(getCurrentUser()));
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody ProjectRequest request) {
        return ResponseEntity.ok(projectService.createProject(request, getCurrentUser()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProject(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProject(id, getCurrentUser()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody ProjectRequest request) {
        return ResponseEntity.ok(projectService.updateProject(id, request, getCurrentUser()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id, getCurrentUser());
        return ResponseEntity.ok("Project deleted successfully");
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<List<com.taskmanager.taskmanager.dto.MemberResponse>> getMembers(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getMembers(id, getCurrentUser()));
    }

    @PostMapping("/{id}/members")
    public ResponseEntity<String> addMember(
            @PathVariable Long id,
            @Valid @RequestBody AddMemberRequest request) {
        return ResponseEntity.ok(projectService.addMember(id, request, getCurrentUser()));
    }

    @DeleteMapping("/{id}/members/{userId}")
    public ResponseEntity<String> removeMember(
            @PathVariable Long id,
            @PathVariable Long userId) {
        projectService.removeMember(id, userId, getCurrentUser());
        return ResponseEntity.ok("Member removed successfully");
    }
}
