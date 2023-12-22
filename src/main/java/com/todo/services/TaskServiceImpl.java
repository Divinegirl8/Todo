package com.todo.services;

import com.todo.data.models.DueDate;
import com.todo.data.models.Task;
import com.todo.data.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{
    @Autowired
    TaskRepository taskRepository;

    @Override
    public Task setTask(String taskId, String userId, LocalDateTime localDateTime, String message, DueDate dueDate, boolean status) {
        Task task = new Task();
        task.setTaskId(taskId);
        task.setUserId(userId);
        task.setLocalDateTime(localDateTime);
        task.setMessage(message);
        task.setDueDate(dueDate);
        task.setStatus(status);
        taskRepository.save(task);
        return task;
    }

    @Override
    public List<Task> findAll() {
       return taskRepository.findAll();
    }
}
