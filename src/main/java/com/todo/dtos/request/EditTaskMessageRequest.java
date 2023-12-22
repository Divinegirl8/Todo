package com.todo.dtos.request;

import lombok.Data;

@Data
public class EditTaskMessageRequest {
    private String userId;
    private String taskId;
    private String message;
    private boolean appendToMessage;
}
