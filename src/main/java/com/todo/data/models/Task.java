package com.todo.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Task {
    @Id
    private String taskId;
    private String message;
    private DueDate dueDate;
    private boolean status;
    private String userId;
}
