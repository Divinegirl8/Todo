package com.todo.exceptions;

public class InvalidDetailsException extends RuntimeException{
    public InvalidDetailsException(){
        System.out.println("Login credentials is not valid");
    }
}
