package com.todo.exceptions;

public class NotLoginException extends RuntimeException{
    public NotLoginException(String message){
        super(message);
    }
}
