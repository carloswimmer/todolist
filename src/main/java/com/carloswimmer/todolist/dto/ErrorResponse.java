package com.carloswimmer.todolist.dto;

import com.carloswimmer.todolist.ApiResponse;

import lombok.Getter;

@Getter
public class ErrorResponse<T> implements ApiResponse<T> {

    private final T data = null;
    private final String message;
    private final boolean success = false;

    public ErrorResponse(String message) {
        this.message = message;
    }

}
