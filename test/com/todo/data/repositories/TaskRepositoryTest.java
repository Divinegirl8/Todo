package com.todo.data.repositories;

import com.todo.data.models.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskRepositoryTest {
@Autowired
    private TaskRepository taskRepository;

@AfterEach
void cleanUp(){
    taskRepository.deleteAll();
}


@Test void save_One_Count_Is_One(){
    Task task = new Task();
    taskRepository.save(task);
    assertEquals(1,taskRepository.count());
}
}