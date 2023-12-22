package com.todo.data.repositories;

import com.todo.data.models.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<Task,String> {
    Task findByTaskId(String taskId);
}
