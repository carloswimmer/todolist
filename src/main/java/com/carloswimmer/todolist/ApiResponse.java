package com.carloswimmer.todolist;

public interface ApiResponse<T> {

    T getData();

    String getMessage();

    boolean isSuccess();

}
