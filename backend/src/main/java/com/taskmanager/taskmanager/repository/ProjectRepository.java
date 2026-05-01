package com.taskmanager.taskmanager.repository;

import com.taskmanager.taskmanager.entity.Project;
import com.taskmanager.taskmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    // Eagerly fetch owner and tasks to avoid LazyInitializationException in toResponse()
    // which calls project.getOwner().getName() and project.getTasks().size()

    @Query("SELECT DISTINCT p FROM Project p " +
           "LEFT JOIN FETCH p.owner " +
           "LEFT JOIN FETCH p.tasks " +
           "WHERE p.owner = :owner")
    List<Project> findByOwner(User owner);

    @Query("SELECT DISTINCT p FROM Project p " +
           "LEFT JOIN FETCH p.owner " +
           "LEFT JOIN FETCH p.tasks " +
           "WHERE p IN (SELECT pm.project FROM ProjectMember pm WHERE pm.user = :user)")
    List<Project> findProjectsByMember(User user);

    @Query("SELECT DISTINCT p FROM Project p " +
           "LEFT JOIN FETCH p.owner " +
           "LEFT JOIN FETCH p.tasks " +
           "WHERE p.id = :id")
    Optional<Project> findByIdWithDetails(Long id);
}
