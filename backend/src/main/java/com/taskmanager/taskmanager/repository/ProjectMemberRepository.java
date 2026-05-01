package com.taskmanager.taskmanager.repository;

import com.taskmanager.taskmanager.entity.Project;
import com.taskmanager.taskmanager.entity.ProjectMember;
import com.taskmanager.taskmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    Optional<ProjectMember> findByUserAndProject(User user, Project project);
    List<ProjectMember> findByProject(Project project);
    boolean existsByUserAndProject(User user, Project project);
}
