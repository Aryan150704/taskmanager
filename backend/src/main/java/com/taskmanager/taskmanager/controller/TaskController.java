package com.taskmanager.taskmanager.controller;

import com.taskmanager.taskmanager.dto.TaskRequest;
import com.taskmanager.taskmanager.dto.TaskResponse;
import com.taskmanager.taskmanager.entity.Task;
import com.taskmanager.taskmanager.repository.UserRepository;
import com.taskmanager.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Task API (all routes require JWT token)
 *
 * GET    /api/tasks/my                  → Get tasks assigned to me
 * GET    /api/tasks/project/{id}        → Get all tasks in a project
 * POST   /api/tasks                     → Create a task
 * PUT    /api/tasks/{id}                → Update a task (full update)
 * PATCH  /api/tasks/{id}/status         → Update task status only
 * DELETE /api/tasks/{id}                → Delete a task
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController extends BaseController {

    private final TaskService taskService;

    public TaskController(UserRepository userRepository, TaskService taskService) {
        super(userRepository);
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllMyTasks() {
        return ResponseEntity.ok(taskService.getMyTasks(getCurrentUser()));
    }

    @GetMapping("/my")
    public ResponseEntity<List<TaskResponse>> getMyTasks() {
        return ResponseEntity.ok(taskService.getMyTasks(getCurrentUser()));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TaskResponse>> getTasksByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(taskService.getTasksByProject(projectId, getCurrentUser()));
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest request) {
        return ResponseEntity.ok(taskService.createTask(request, getCurrentUser()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request) {
        return ResponseEntity.ok(taskService.updateTask(id, request, getCurrentUser()));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam Task.Status status) {
        return ResponseEntity.ok(taskService.updateTaskStatus(id, status, getCurrentUser()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id, getCurrentUser());
        return ResponseEntity.ok("Task deleted successfully");
    }
}
