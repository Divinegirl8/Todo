package com.todo.dtos.request;

import com.todo.data.models.DueDate;
import lombok.Data;

@Data
public class EditTaskDateRequest {
    private String userId;
    private String taskId;
    private DueDate dueDate;
}
