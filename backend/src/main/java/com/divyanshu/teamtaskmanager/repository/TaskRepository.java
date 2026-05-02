package com.divyanshu.teamtaskmanager.repository;

import com.divyanshu.teamtaskmanager.entity.Project;
import com.divyanshu.teamtaskmanager.entity.Task;
import com.divyanshu.teamtaskmanager.entity.TaskStatus;
import com.divyanshu.teamtaskmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAssignedTo(User user);

    List<Task> findByProject(Project project);

    List<Task> findByDueDateBeforeAndStatusNot(
            LocalDate date,
            TaskStatus status
    );

    long countByStatus(TaskStatus status);

    long countByOverdue(Boolean overdue);
}