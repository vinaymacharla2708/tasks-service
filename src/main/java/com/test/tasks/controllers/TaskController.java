package com.test.tasks.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.tasks.entity.Task;
import com.test.tasks.entity.TaskStatistics;
import com.test.tasks.entity.TaskStatus;
import com.test.tasks.services.PriorityBasedTaskQueueService;
import com.test.tasks.services.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    private final PriorityBasedTaskQueueService queueService;

    public TaskController(TaskService taskService, PriorityBasedTaskQueueService queueService) {
        this.taskService = taskService;
        this.queueService = queueService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }
    
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        task.setStatus(TaskStatus.TODO);
        Task createdTask = taskService.createTask(task);
        return ResponseEntity.ok(createdTask);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTask(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody Task updatedTask) {
        Task task = taskService.updateTask(taskId, updatedTask);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{taskId}/assign")
    public ResponseEntity<?> assignTask(@PathVariable Long taskId, @RequestBody Long userId) {
        try {
            Task assignedTask = taskService.assignTask(taskId, userId);
            return ResponseEntity.ok(assignedTask);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping("/{taskId}/progress")
    public ResponseEntity<Task> setTaskProgress(@PathVariable Long taskId, @RequestBody Long progress) {
        Task updatedTask = taskService.setTaskProgress(taskId, progress);
        return ResponseEntity.ok(updatedTask);
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<Task>> getOverdueTasks() {
        List<Task> overdueTasks = taskService.getOverdueTasks();
        return ResponseEntity.ok(overdueTasks);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable TaskStatus status) {
        List<Task> tasks = taskService.getTasksByStatus(status);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/completed")
    public ResponseEntity<List<Task>> getCompletedTasksByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<Task> completedTasks = taskService.getCompletedTasksByDateRange(startDate, endDate);
        return ResponseEntity.ok(completedTasks);
    }

    @GetMapping("/statistics")
    public ResponseEntity<TaskStatistics> getTasksStatistics() {
        TaskStatistics statistics = taskService.getTasksStatistics();
        return ResponseEntity.ok(statistics);
    }

    @GetMapping("/priorityQueue")
    public ResponseEntity<List<Task>> getTasksInPriorityOrder() {
        List<Task> tasks = queueService.getTasksInPriorityOrder();
        return ResponseEntity.ok(tasks);
    }

}
