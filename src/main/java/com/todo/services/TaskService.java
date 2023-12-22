package com.todo.services;

import com.todo.data.models.DueDate;
import com.todo.data.models.Task;

import java.util.List;

public interface TaskService {
    Task setTask(String taskId, String userId, String message, DueDate dueDate,boolean status);
    List<Task> findAll();


}
