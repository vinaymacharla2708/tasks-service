package com.test.tasks.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.tasks.entity.Task;
import com.test.tasks.entity.TaskStatus;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(TaskStatus status);
    List<Task> findByDueDateBefore(LocalDate date);
    List<Task> findByCompletedDateBetween(LocalDate startDate, LocalDate endDate);
    List<Task> findByUserId(Long userId);
    List<Task> findByDueDateBeforeAndStatusNot(LocalDate currentDate, TaskStatus completed);
    List<Task> findByStatusAndCompletedDateBetween(TaskStatus completed, LocalDate startDate, LocalDate endDate);
    List<Task> findByStatusIn(List<TaskStatus> asList);
}