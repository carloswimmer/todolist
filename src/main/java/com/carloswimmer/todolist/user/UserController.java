package com.carloswimmer.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carloswimmer.todolist.ApiResponse;
import com.carloswimmer.todolist.dto.SuccessResponse;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse<UserModel>> create(@RequestBody UserModel userModel) {
        var userCreated = userService.create(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(userCreated));
    }
}
