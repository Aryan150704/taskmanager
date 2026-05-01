package com.taskmanager.taskmanager.service;

import com.taskmanager.taskmanager.dto.TaskRequest;
import com.taskmanager.taskmanager.dto.TaskResponse;
import com.taskmanager.taskmanager.entity.Project;
import com.taskmanager.taskmanager.entity.Task;
import com.taskmanager.taskmanager.entity.User;
import com.taskmanager.taskmanager.repository.TaskRepository;
import com.taskmanager.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectService projectService;

    public TaskService(TaskRepository taskRepository,
                       UserRepository userRepository,
                       ProjectService projectService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectService = projectService;
    }

    /**
     * Create a new task (any project member can create)
     */
    @Transactional
    public TaskResponse createTask(TaskRequest request, User currentUser) {
        Project project = projectService.findProjectById(request.getProjectId());
        projectService.ensureIsMember(project, currentUser);

        User assignee = null;
        if (request.getAssigneeId() != null) {
            assignee = userRepository.findById(request.getAssigneeId())
                    .orElseThrow(() -> new RuntimeException("Assignee not found"));
            // BUG 5 FIX: give a clear error message so user knows it's about the ASSIGNEE, not themselves
            try {
                projectService.ensureIsMember(project, assignee);
            } catch (RuntimeException e) {
                throw new RuntimeException("Assignee must be a member of this project");
            }
        }

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus() != null ? request.getStatus() : Task.Status.TODO)
                .dueDate(request.getDueDate())
                .project(project)
                .assignee(assignee)
                .createdBy(currentUser)
                .build();

        return toResponse(taskRepository.save(task));
    }

    /**
     * Get all tasks in a project
     */
    @Transactional(readOnly = true)
    public List<TaskResponse> getTasksByProject(Long projectId, User currentUser) {
        Project project = projectService.findProjectById(projectId);
        projectService.ensureIsMember(project, currentUser);
        return taskRepository.findByProject(project).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get tasks assigned to the current user
     */
    @Transactional(readOnly = true)
    public List<TaskResponse> getMyTasks(User currentUser) {
        return taskRepository.findByAssignee(currentUser).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Update a task (project members can update)
     */
    @Transactional
    public TaskResponse updateTask(Long taskId, TaskRequest request, User currentUser) {
        Task task = findTaskById(taskId);
        projectService.ensureIsMember(task.getProject(), currentUser);

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());

        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }
        if (request.getDueDate() != null) {
            task.setDueDate(request.getDueDate());
        }

        if (request.getAssigneeId() != null) {
            User assignee = userRepository.findById(request.getAssigneeId())
                    .orElseThrow(() -> new RuntimeException("Assignee not found"));
            projectService.ensureIsMember(task.getProject(), assignee);
            task.setAssignee(assignee);
        } else {
            task.setAssignee(null);
        }

        return toResponse(taskRepository.save(task));
    }

    /**
     * Update just the status of a task (quick action)
     */
    @Transactional
    public TaskResponse updateTaskStatus(Long taskId, Task.Status newStatus, User currentUser) {
        Task task = findTaskById(taskId);
        projectService.ensureIsMember(task.getProject(), currentUser);
        task.setStatus(newStatus);
        return toResponse(taskRepository.save(task));
    }

    /**
     * Delete a task (ADMIN or task creator)
     */
    @Transactional
    public void deleteTask(Long taskId, User currentUser) {
        Task task = findTaskById(taskId);

        boolean isAdmin = false;
        try {
            projectService.ensureIsAdmin(task.getProject(), currentUser);
            isAdmin = true;
        } catch (RuntimeException ignored) {}

        boolean isCreator = task.getCreatedBy().getId().equals(currentUser.getId());

        if (!isAdmin && !isCreator) {
            throw new RuntimeException("Only admins or the task creator can delete this task");
        }

        taskRepository.delete(task);
    }

    // ── Helper Methods ────────────────────────────────────────────

    public Task findTaskById(Long id) {
        // BUG 2 FIX: use JOIN FETCH query so project/assignee/createdBy are always loaded.
        // Plain findById() returns lazy proxies → LazyInitializationException in toResponse().
        return taskRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    }

    public TaskResponse toResponse(Task task) {
        boolean isOverdue = task.getDueDate() != null
                && task.getDueDate().isBefore(LocalDate.now())
                && task.getStatus() != Task.Status.DONE;

        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .dueDate(task.getDueDate())
                .overdue(isOverdue)
                .projectId(task.getProject().getId())
                .projectName(task.getProject().getName())
                .assigneeId(task.getAssignee() != null ? task.getAssignee().getId() : null)
                .assigneeName(task.getAssignee() != null ? task.getAssignee().getName() : null)
                .createdByName(task.getCreatedBy().getName())
                .createdAt(task.getCreatedAt())
                .build();
    }
}
