package com.todo.services;

import com.todo.data.models.DueDate;
import com.todo.data.models.Task;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskService {
    Task setTask(String taskId, String userId, LocalDateTime localDateTime, String message, DueDate dueDate, boolean status);
    List<Task> findAll();
    Task completeTask(String userId,String taskId);
    Task editDueDate(String taskId, DueDate dueDate);
    Task editMessage(String taskId, String message,boolean appendToMessage);


}
