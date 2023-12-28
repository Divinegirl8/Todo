package com.todo.dtos.request;

import lombok.Data;

@Data
public class FindTaskRequest {
    private String userId;
    private String taskId;
}
