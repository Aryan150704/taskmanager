package com.taskmanager.taskmanager.service;

import com.taskmanager.taskmanager.dto.DashboardResponse;
import com.taskmanager.taskmanager.dto.ProjectResponse;
import com.taskmanager.taskmanager.dto.TaskResponse;
import com.taskmanager.taskmanager.entity.Project;
import com.taskmanager.taskmanager.entity.Task;
import com.taskmanager.taskmanager.entity.User;
import com.taskmanager.taskmanager.repository.ProjectRepository;
import com.taskmanager.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final ProjectService projectService;
    private final TaskService taskService;

    public DashboardService(ProjectRepository projectRepository,
                            TaskRepository taskRepository,
                            ProjectService projectService,
                            TaskService taskService) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.projectService = projectService;
        this.taskService = taskService;
    }

    /**
     * Get all dashboard data for the current user
     */
    @Transactional(readOnly = true)
    public DashboardResponse getDashboard(User currentUser) {
        List<Project> myProjects = projectRepository.findProjectsByMember(currentUser);
        List<Task> myTasks = taskRepository.findByAssignee(currentUser);

        List<Task> overdueTasks = myProjects.stream()
                .flatMap(p -> taskRepository.findOverdueTasksByProject(p, LocalDate.now()).stream())
                .collect(Collectors.toList());

        long totalTasks = myProjects.stream()
                .mapToLong(p -> taskRepository.findByProject(p).size())
                .sum();

        long todoCount = myProjects.stream()
                .mapToLong(p -> taskRepository.countByProjectAndStatus(p, Task.Status.TODO))
                .sum();

        long inProgressCount = myProjects.stream()
                .mapToLong(p -> taskRepository.countByProjectAndStatus(p, Task.Status.IN_PROGRESS))
                .sum();

        long doneCount = myProjects.stream()
                .mapToLong(p -> taskRepository.countByProjectAndStatus(p, Task.Status.DONE))
                .sum();

        // FIX: projectService.toResponse() is now public (was private before → compile error)
        List<ProjectResponse> projectResponses = myProjects.stream()
                .map(p -> projectService.toResponse(p, currentUser))
                .collect(Collectors.toList());

        List<TaskResponse> myTaskResponses = myTasks.stream()
                .map(taskService::toResponse)
                .collect(Collectors.toList());

        List<TaskResponse> overdueTaskResponses = overdueTasks.stream()
                .map(taskService::toResponse)
                .collect(Collectors.toList());

        return DashboardResponse.builder()
                .totalProjects(myProjects.size())
                .totalTasks((int) totalTasks)
                .todoCount((int) todoCount)
                .inProgressCount((int) inProgressCount)
                .doneCount((int) doneCount)
                .overdueCount(overdueTasks.size())
                .myTasks(myTaskResponses)
                .overdueTasks(overdueTaskResponses)
                .myProjects(projectResponses)
                .build();
    }
}
