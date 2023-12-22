package com.todo.dtos.request;

import lombok.Data;

@Data
public class CompleteTaskRequest {
    private String userId;
    private String taskId;
}
