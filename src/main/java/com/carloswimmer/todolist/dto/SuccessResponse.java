package com.carloswimmer.todolist.dto;

import com.carloswimmer.todolist.ApiResponse;

import lombok.Getter;

@Getter
public class SuccessResponse<T> implements ApiResponse<T> {

    private final T data;
    private final String message = null;
    private final boolean success = true;

    public SuccessResponse(T data) {
        this.data = data;
    }
}
