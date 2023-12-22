package com.todo.dtos.request;

import lombok.Data;

@Data
public class DeleteAccountRequest {
    private String username;
    private  String password;
}
