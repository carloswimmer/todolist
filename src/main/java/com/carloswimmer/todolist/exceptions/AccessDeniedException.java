package com.carloswimmer.todolist.exceptions;

public class AccessDeniedException extends BusinessException {

    public AccessDeniedException() {
        super("You are not authorized to access this resource");
    }

    public AccessDeniedException(String message) {
        super(message);
    }

}
