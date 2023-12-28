package com.todo.services;

import com.todo.data.models.DueDate;
import com.todo.data.models.Task;
import com.todo.data.repositories.TaskRepository;
import com.todo.exceptions.TaskIdNotFoundException;
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

    @Override
    public Task completeTask(String userId,String taskId) {
        Task task = taskRepository.findByTaskId(taskId);
        if (task == null) throw new TaskIdNotFoundException(taskId + " not found");

        task.setUserId(userId);
        task.setStatus(true);
        taskRepository.save(task);
        return task;
    }

    @Override
    public Task editDueDate(String taskId, DueDate dueDate) {
        Task task = taskRepository.findByTaskId(taskId);

        if (task == null) throw new TaskIdNotFoundException(taskId + " not found");

        task.setDueDate(dueDate);
        taskRepository.save(task);
        return task;
    }

    @Override
    public Task editMessage(String taskId, String message, boolean appendToMessage) {
        Task task = taskRepository.findByTaskId(taskId);

        if (task == null) throw new TaskIdNotFoundException(taskId + " not found");

        if (appendToMessage){
            String newMessage = task.getMessage() + " " + message;
            task.setMessage(newMessage);
        } else {
            task.setMessage(message);
        }
        taskRepository.save(task);
        return task;
    }
}
