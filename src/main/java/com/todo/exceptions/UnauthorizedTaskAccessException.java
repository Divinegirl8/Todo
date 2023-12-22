package com.todo.exceptions;

public class UnauthorizedTaskAccessException extends RuntimeException{
    public UnauthorizedTaskAccessException(String message){
        super(message);
    }
}
