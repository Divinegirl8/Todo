package com.todo.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class User {
    @Id
    private String userId;
    private String userName;
    private String password;
    private boolean isLogin;
    private int taskId;
}
