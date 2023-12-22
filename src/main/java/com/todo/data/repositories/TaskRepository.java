package com.todo.data.repositories;

import com.todo.data.models.Task;
import com.todo.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task,String> {
    Task findByTaskId(String taskId);
    List<Task> findByUserId(String userId);
}
