# API's

TaskManager-Service

## 1. Create task

Api: POST - */api/tasks*

Request Body:
```Json
{
    "title": "Java Coding Challenge",
    "description": "Complete the Java coding challenge for the task service.",
    "dueDate": "2023-08-15"
}
```
Response:
```Json
{
    "id": 4,
    "title": "Finish Java Coding Challenge",
    "description": "Complete the Java coding challenge for the task service.",
    "dueDate": "2023-08-08",
    "status": "TODO",
    "priority": "LOW",
    "completedDate": null,
    "progress": null,
    "user": null
}
```

## 2. Update task:

Api: PUT - */api/tasks/4*

Request Body:
```Json
{
    "title": "Java Coding Challenge",
    "description": "Complete the Java coding challenge for the task service.",
    "dueDate": "2023-08-15",
    "priority": "MEDIUM",
}
```
Response:
```Json
{
    "id": 4,
    "title": "Finish Java Coding Challenge",
    "description": "Complete the Java coding challenge for the task service.",
    "dueDate": "2023-08-08",
    "status": "TODO",
    "priority": "MEDIUM",
    "completedDate": null,
    "progress": null,
    "user": null
}
```

## 3. Fetch all tasks:

Api: GET - */api/tasks*

Response:
```Json
[
  {
    "id": 4,
    "title": "Finish Java Coding Challenge",
    "description": "Complete the Java coding challenge for the task service.",
    "dueDate": "2023-08-08",
    "status": "TODO",
    "priority": "MEDIUM",
    "completedDate": null,
    "progress": null,
    "user": null
  }
]
```

## 4. Fetch task by id:

Api: GET - */api/tasks/4*

Response:
```Json
{
    "id": 4,
    "title": "Finish Java Coding Challenge",
    "description": "Complete the Java coding challenge for the task service.",
    "dueDate": "2023-08-08",
    "status": "TODO",
    "priority": "MEDIUM",
    "completedDate": null,
    "progress": null,
    "user": null
}
```

## 5. Delete task:

Api: DELETE - */api/tasks/4*

Response - 204 no-content


## 6. Assign user:

Api: POST - */api/tasks/4/assign*

Request Body:
```Json
101
```
Response:
```Json
{
    "id": 4,
    "title": "Finish Java Coding Challenge",
    "description": "Complete the Java coding challenge for the task service.",
    "dueDate": "2023-08-09",
    "status": "TODO",
    "priority": "MEDIUM",
    "completedDate": null,
    "progress": 30,
    "user": {
        "id": 101,
        "name": "testuser1"
    }
}
```


## 7. Get User assigned tasks:

Api: GET - */api/users/101/tasks*

Response:
```Json
[
  {
    "id": 4,
    "title": "Finish Java Coding Challenge",
    "description": "Complete the Java coding challenge for the task service.",
    "dueDate": "2023-08-09",
    "status": "TODO",
    "priority": "MEDIUM",
    "completedDate": null,
    "progress": 30,
    "user": {
        "id": 101,
        "name": "testuser1"
    }
  }
]
```


## 8. progress of tasks:

Api: PUT - */api/tasks/4/progress*

Request Body:
```Json
40
```
Response:
```Json
{
    "id": 4,
    "title": "Finish Java Coding Challenge",
    "description": "Complete the Java coding challenge for the task service.",
    "dueDate": "2023-08-08",
    "status": "TODO",
    "priority": "MEDIUM",
    "completedDate": null,
    "progress": 40,
    "user": {
        "id": 101,
        "name": "testuser1"
    }
}
```


## 9. Overdue tasks:

Api: GET - */api/tasks/overdue*

Response:
```Json
[
  {
    "id": 2,
    "title": "Finish Java Coding Challenge",
    "description": "Complete the Java coding challenge for the task service.",
    "dueDate": "2023-08-01",
    "status": "TODO",
    "priority": "MEDIUM",
    "completedDate": null,
    "progress": null,
    "user": null
  }
]
```

## 10. filter tasks by status:

Api: GET - */api/tasks/status/COMPLETED*


Response:
```Json
{
    "id": 3,
    "title": "Finish Java Coding Challenge",
    "description": "Complete the Java coding challenge for the task service.",
    "dueDate": "2023-08-09",
    "status": "COMPLETED",
    "priority": "HIGH",
    "completedDate": 2023-08-04,
    "progress": 100,
    "user": {
        "id": 101,
        "name": "testuser1"
    }
}
```

## 11. Get Completed tasks by date:

Api: GET - */api/tasks/completed?startDate=2023-08-04&endDate=2023-08-15*

Response:
```Json
[
  {
    "id": 3,
    "title": "Finish Java Coding Challenge",
    "description": "Complete the Java coding challenge for the task service.",
    "dueDate": "2023-08-09",
    "status": "COMPLETED",
    "priority": "HIGH",
    "completedDate": 2023-08-04,
    "progress": 100,
    "user": {
        "id": 101,
        "name": "testuser1"
    }
  }
]
```

## 12. Get statistics of tasks:

Api: GET - */api/tasks/statistics*

Response:
```Json
{
    "totalTasks": 4,
    "completedTasks": 1,
    "percentageCompleted": 25.0
}
```


## 13. Get tasks based on its priority:

Api: GET - */api/tasks/priorityQueue*

Response:
```Json
[
    {
        "id": 4,
        "title": "Finish Java Coding Challenge",
        "description": "Complete the Java coding challenge for the task service.",
        "dueDate": "2023-08-08",
        "status": "TODO",
        "priority": "LOW",
        "completedDate": null,
        "progress": null,
        "user": null
    },
    {
        "id": 1,
        "title": "Finish Java Coding Challenge",
        "description": "Complete the Java coding challenge for the task service.",
        "dueDate": "2023-08-09",
        "status": "TODO",
        "priority": "MEDIUM",
        "completedDate": null,
        "progress": 30,
        "user": {
            "id": 2,
            "name": "vamsi"
        }
    },
    {
        "id": 3,
        "title": "Finish Java Coding Challenge",
        "description": "Complete the Java coding challenge for the task service.",
        "dueDate": "2023-08-09",
        "status": "TODO",
        "priority": "LOW",
        "completedDate": null,
        "progress": null,
        "user": null
    }
]
```

