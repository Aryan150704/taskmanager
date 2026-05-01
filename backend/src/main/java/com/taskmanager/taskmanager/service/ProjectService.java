package com.taskmanager.taskmanager.service;

import com.taskmanager.taskmanager.dto.AddMemberRequest;
import com.taskmanager.taskmanager.dto.ProjectRequest;
import com.taskmanager.taskmanager.dto.ProjectResponse;
import com.taskmanager.taskmanager.entity.Project;
import com.taskmanager.taskmanager.entity.ProjectMember;
import com.taskmanager.taskmanager.entity.User;
import com.taskmanager.taskmanager.repository.ProjectMemberRepository;
import com.taskmanager.taskmanager.repository.ProjectRepository;
import com.taskmanager.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository,
                          ProjectMemberRepository projectMemberRepository,
                          UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.projectMemberRepository = projectMemberRepository;
        this.userRepository = userRepository;
    }

    /**
     * Create a new project — the creator becomes ADMIN automatically
     */
    @Transactional
    public ProjectResponse createProject(ProjectRequest request, User currentUser) {
        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .owner(currentUser)
                .members(new ArrayList<>())
                .tasks(new ArrayList<>())
                .build();

        Project saved = projectRepository.save(project);

        // Auto-add creator as ADMIN member
        ProjectMember adminMember = ProjectMember.builder()
                .user(currentUser)
                .project(saved)
                .role(ProjectMember.Role.ADMIN)
                .build();
        projectMemberRepository.save(adminMember);

        return toResponse(saved, currentUser);
    }

    /**
     * Get all projects the current user is part of
     */
    @Transactional(readOnly = true)
    public List<ProjectResponse> getMyProjects(User currentUser) {
        List<Project> projects = projectRepository.findProjectsByMember(currentUser);
        return projects.stream()
                .map(p -> toResponse(p, currentUser))
                .collect(Collectors.toList());
    }

    /**
     * Get a single project by ID
     */
    @Transactional(readOnly = true)
    public ProjectResponse getProject(Long projectId, User currentUser) {
        Project project = findProjectById(projectId);
        ensureIsMember(project, currentUser);
        return toResponse(project, currentUser);
    }

    /**
     * Update a project (ADMIN only)
     */
    @Transactional
    public ProjectResponse updateProject(Long projectId, ProjectRequest request, User currentUser) {
        Project project = findProjectById(projectId);
        ensureIsAdmin(project, currentUser);

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        return toResponse(projectRepository.save(project), currentUser);
    }

    /**
     * Delete a project (OWNER only)
     */
    @Transactional
    public void deleteProject(Long projectId, User currentUser) {
        Project project = findProjectById(projectId);
        if (!project.getOwner().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Only the project owner can delete this project");
        }
        projectRepository.delete(project);
    }

    /**
     * Add a member to a project (ADMIN only)
     */
    @Transactional
    public String addMember(Long projectId, AddMemberRequest request, User currentUser) {
        Project project = findProjectById(projectId);
        ensureIsAdmin(project, currentUser);

        User newMember = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found: " + request.getEmail()));

        if (projectMemberRepository.existsByUserAndProject(newMember, project)) {
            throw new RuntimeException("User is already a member of this project");
        }

        ProjectMember member = ProjectMember.builder()
                .user(newMember)
                .project(project)
                .role(request.getRole())
                .build();
        projectMemberRepository.save(member);

        return newMember.getName() + " added as " + request.getRole();
    }

    /**
     * Get all members of a project (BUG 3 FIX — was missing)
     */
    @Transactional(readOnly = true)
    public List<com.taskmanager.taskmanager.dto.MemberResponse> getMembers(Long projectId, User currentUser) {
        Project project = findProjectById(projectId);
        ensureIsMember(project, currentUser);
        return projectMemberRepository.findByProject(project).stream()
                .map(m -> new com.taskmanager.taskmanager.dto.MemberResponse(
                        m.getUser().getId(),
                        m.getUser().getName(),
                        m.getUser().getEmail(),
                        m.getRole().name()
                ))
                .collect(Collectors.toList());
    }

    /**
     */
    @Transactional
    public void removeMember(Long projectId, Long userId, User currentUser) {
        Project project = findProjectById(projectId);
        ensureIsAdmin(project, currentUser);

        User userToRemove = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (project.getOwner().getId().equals(userId)) {
            throw new RuntimeException("Cannot remove the project owner");
        }

        ProjectMember member = projectMemberRepository.findByUserAndProject(userToRemove, project)
                .orElseThrow(() -> new RuntimeException("User is not a member of this project"));

        projectMemberRepository.delete(member);
    }

    // ── Helper Methods (package-accessible so other services can use them) ──

    public Project findProjectById(Long id) {
        return projectRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
    }

    public void ensureIsMember(Project project, User user) {
        if (!projectMemberRepository.existsByUserAndProject(user, project)) {
            throw new RuntimeException("You are not a member of this project");
        }
    }

    public void ensureIsAdmin(Project project, User user) {
        ProjectMember membership = projectMemberRepository.findByUserAndProject(user, project)
                .orElseThrow(() -> new RuntimeException("You are not a member of this project"));
        if (membership.getRole() != ProjectMember.Role.ADMIN) {
            throw new RuntimeException("Only admins can perform this action");
        }
    }

    /**
     * FIX #5: This was private in the original code — DashboardService calls it,
     * so it MUST be public. This caused a compilation error before.
     */
    public ProjectResponse toResponse(Project project, User currentUser) {
        List<ProjectMember> members = projectMemberRepository.findByProject(project);

        String currentUserRole = members.stream()
                .filter(m -> m.getUser().getId().equals(currentUser.getId()))
                .map(m -> m.getRole().name())
                .findFirst()
                .orElse("NONE");

        int taskCount = project.getTasks() != null ? project.getTasks().size() : 0;

        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .ownerName(project.getOwner().getName())
                .ownerEmail(project.getOwner().getEmail())
                .memberCount(members.size())
                .taskCount(taskCount)
                .createdAt(project.getCreatedAt())
                .currentUserRole(currentUserRole)
                .build();
    }
}
