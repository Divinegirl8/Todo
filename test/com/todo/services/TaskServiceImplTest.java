package com.todo.services;

import com.todo.data.models.Date;
import com.todo.data.models.DueDate;
import com.todo.data.models.Time;
import com.todo.data.repositories.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskServiceImplTest {
    @Autowired
    TaskService taskService;
    @Autowired
    TaskRepository taskRepository;

    @AfterEach
    void cleanUp(){
        taskRepository.deleteAll();
    }

    @Test void addTask_Count_Is_One(){
        Date date = new Date();
        date.setMonth("October");
        date.setDay("9");
        date.setYear("2023");

        Time time = new Time();
        time.setHours("9");
        time.setMinutes("15");
        time.setSeconds("24");

        DueDate dueDate = new DueDate();
        dueDate.setDate(date);
        dueDate.setTime(time);

        taskService.setTask("1", "1", LocalDateTime.now(),"Reading", dueDate, false);
        assertEquals(1,taskRepository.count());
    }

}