package com.test.tasks.services;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.test.tasks.entity.Task;
import com.test.tasks.entity.TaskStatus;
import com.test.tasks.repositories.TaskRepository;

@Service
public class PriorityBasedTaskQueueService {
    private final TaskRepository taskRepository;

    public PriorityBasedTaskQueueService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasksInPriorityOrder() {
        List<Task> tasks = taskRepository.findByStatusIn(Arrays.asList(TaskStatus.IN_PROGRESS, TaskStatus.TODO));
        tasks.sort(Comparator.comparing(Task::getDueDate).thenComparing(Task::getPriority));
        return tasks;
    }

}

