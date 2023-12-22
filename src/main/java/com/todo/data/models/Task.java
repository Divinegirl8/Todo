package com.todo.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class Task {
    @Id
    private String taskId;
    private String message;
    private LocalDateTime localDateTime;
    private DueDate dueDate;
    private boolean status;
    private String userId;
}
