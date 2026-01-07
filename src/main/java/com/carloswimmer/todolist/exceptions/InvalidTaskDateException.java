package com.carloswimmer.todolist.exceptions;

public class InvalidTaskDateException extends BusinessException {

    public InvalidTaskDateException(String message) {
        super(message);
    }

}
