package com.test.tasks.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.test.tasks.entity.Task;
import com.test.tasks.entity.TaskStatistics;
import com.test.tasks.entity.TaskStatus;
import com.test.tasks.entity.User;
import com.test.tasks.repositories.TaskRepository;
import com.test.tasks.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task getTaskById(Long id) {
        Optional<Task> existingTask = taskRepository.findById(id);
        if (existingTask.isPresent())
            return existingTask.get();
        return null;
    }

    public Task updateTask(Long taskId, Task updatedTask) {
        // Retrieve the existing task from the database.
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + taskId));

        // Update the properties of the existing task with the provided values from updatedTask.
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setDueDate(updatedTask.getDueDate());
        existingTask.setStatus(updatedTask.getStatus());
        existingTask.setPriority(updatedTask.getPriority());

        // If the status is set to "Completed," set the completed date.
        if (TaskStatus.COMPLETED.equals(updatedTask.getStatus())) {
            existingTask.setCompletedDate(LocalDate.now());
        } else {
            existingTask.setCompletedDate(null);
        }

        // Save the updated task to the database.
        return taskRepository.save(existingTask);
    }

    public void deleteTask(Long taskId) {
        // Check if the task exists in the database.
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + taskId));

        // Delete the task from the database.
        taskRepository.delete(existingTask);
    }
    
    public Task assignTask(Long taskId, Long userId) {
        // Retrieve the existing task from the database.
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + taskId));

        // Retrieve the user from the database.
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        // Assign the task to the user.
        existingTask.setUser(user);

        // Save the updated task to the database.
        return taskRepository.save(existingTask);
    }

    public Task setTaskProgress(Long taskId, Long progress) {
        // Retrieve the existing task from the database.
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + taskId));

        // Ensure progress is within the valid range (0 to 100).
        if (progress < 0 || progress > 100) {
            throw new IllegalArgumentException("Progress should be between 0 and 100.");
        }

        // Set the progress percentage of the task.
        existingTask.setProgress(progress);

        // Save the updated task to the database.
        return taskRepository.save(existingTask);
    }

    public List<Task> getUserAssignedTasks(Long userId) {
        // Retrieve all tasks assigned to the specified user from the database.
        return taskRepository.findByUserId(userId);
    }

    public List<Task> getOverdueTasks() {
        LocalDate currentDate = LocalDate.now();

        // Retrieve all tasks that have due dates earlier than the current date.
        return taskRepository.findByDueDateBeforeAndStatusNot(currentDate, TaskStatus.COMPLETED);
    }

    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    public List<Task> getCompletedTasksByDateRange(LocalDate startDate, LocalDate endDate) {
        return taskRepository.findByStatusAndCompletedDateBetween(TaskStatus.COMPLETED, startDate, endDate);
    }

    public TaskStatistics getTasksStatistics() {
        TaskStatistics statistics = new TaskStatistics();
        List<Task> allTasks = taskRepository.findAll();
        long totalTasks = allTasks.size();
        long completedTasks = allTasks.stream().filter(task -> task.getStatus() == TaskStatus.COMPLETED).count();
        double percentageCompleted = (completedTasks * 100.0) / totalTasks;

        statistics.setTotalTasks(totalTasks);
        statistics.setCompletedTasks(completedTasks);
        statistics.setPercentageCompleted(percentageCompleted);

        return statistics;
    }

}
