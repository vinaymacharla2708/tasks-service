package com.test.tasks.entity;

import lombok.Data;

@Data
public class TaskStatistics {
    private long totalTasks;
    private long completedTasks;
    private double percentageCompleted;
}
