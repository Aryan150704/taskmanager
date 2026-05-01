package com.taskmanager.taskmanager.repository;

import com.taskmanager.taskmanager.entity.Project;
import com.taskmanager.taskmanager.entity.Task;
import com.taskmanager.taskmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // JOIN FETCH all lazy relations that toResponse() accesses:
    // task.getProject().getName(), task.getCreatedBy().getName(), task.getAssignee().getName()
    // Without this, Hibernate throws LazyInitializationException:
    // "failed to lazily initialize collection — could not initialize proxy – no Session"

    @Query("SELECT t FROM Task t " +
           "LEFT JOIN FETCH t.project " +
           "LEFT JOIN FETCH t.assignee " +
           "LEFT JOIN FETCH t.createdBy " +
           "WHERE t.project = :project")
    List<Task> findByProject(Project project);

    @Query("SELECT t FROM Task t " +
           "LEFT JOIN FETCH t.project " +
           "LEFT JOIN FETCH t.assignee " +
           "LEFT JOIN FETCH t.createdBy " +
           "WHERE t.assignee = :assignee")
    List<Task> findByAssignee(User assignee);

    @Query("SELECT t FROM Task t " +
           "LEFT JOIN FETCH t.project " +
           "LEFT JOIN FETCH t.assignee " +
           "LEFT JOIN FETCH t.createdBy " +
           "WHERE t.dueDate < :today AND t.status != 'DONE'")
    List<Task> findOverdueTasks(LocalDate today);

    @Query("SELECT t FROM Task t " +
           "LEFT JOIN FETCH t.project " +
           "LEFT JOIN FETCH t.assignee " +
           "LEFT JOIN FETCH t.createdBy " +
           "WHERE t.project = :project AND t.dueDate < :today AND t.status != 'DONE'")
    List<Task> findOverdueTasksByProject(Project project, LocalDate today);

    long countByProjectAndStatus(Project project, Task.Status status);

    // FIX BUG 2: plain findById() leaves project/assignee/createdBy lazy-uninitialized.
    // Use this in TaskService.findTaskById() so toResponse() never hits LazyInitializationException.
    @Query("SELECT t FROM Task t " +
           "LEFT JOIN FETCH t.project " +
           "LEFT JOIN FETCH t.assignee " +
           "LEFT JOIN FETCH t.createdBy " +
           "WHERE t.id = :id")
    java.util.Optional<Task> findByIdWithDetails(Long id);
}
