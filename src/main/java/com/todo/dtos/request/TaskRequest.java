package com.todo.dtos.request;

import com.todo.data.models.DueDate;
import lombok.Data;

@Data
public class TaskRequest {
    private String userId;
    private String Message;
    private DueDate dueDate;
}
