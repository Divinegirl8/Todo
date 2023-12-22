package com.todo.exceptions;

public class NoUserExist extends RuntimeException{
    public NoUserExist(String message){
        super(message);
    }
}
