package com.todo.dtos.request;

import lombok.Data;

@Data
public class DeleteTaskRequest {
    private String userId;
    private String taskId;
    private String password;
}
