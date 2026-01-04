package com.carloswimmer.todolist.dto;

import com.carloswimmer.todolist.ApiResponse;

import lombok.Getter;

public class ErrorResponse implements ApiResponse {

    @Getter
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

}
