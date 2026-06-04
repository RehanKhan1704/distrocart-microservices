package com.example.demo.exception;

public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(String username){
        super("User "+username+" Already Exists");
    }
}
