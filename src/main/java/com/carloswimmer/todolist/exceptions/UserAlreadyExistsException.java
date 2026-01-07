package com.carloswimmer.todolist.exceptions;

public class UserAlreadyExistsException extends BusinessException {

    public UserAlreadyExistsException() {
        super("User already exists");
    }

}
