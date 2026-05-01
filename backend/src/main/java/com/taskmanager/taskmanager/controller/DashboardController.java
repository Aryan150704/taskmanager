package com.taskmanager.taskmanager.controller;

import com.taskmanager.taskmanager.dto.DashboardResponse;
import com.taskmanager.taskmanager.repository.UserRepository;
import com.taskmanager.taskmanager.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Dashboard API
 *
 * GET /api/dashboard → Returns summary data for the logged-in user:
 *   - total projects, tasks, overdue count
 *   - tasks assigned to me
 *   - my projects
 *   - overdue tasks across my projects
 */
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController extends BaseController {

    private final DashboardService dashboardService;

    public DashboardController(UserRepository userRepository, DashboardService dashboardService) {
        super(userRepository);
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboard() {
        return ResponseEntity.ok(dashboardService.getDashboard(getCurrentUser()));
    }
}
