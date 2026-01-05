package com.carloswimmer.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carloswimmer.todolist.ApiResponse;
import com.carloswimmer.todolist.dto.ErrorResponse;
import com.carloswimmer.todolist.dto.SuccessResponse;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity<ApiResponse<UserModel>> create(@RequestBody UserModel userModel) {
        var user = userRepository.findByUsername(userModel.getUsername());

        if (user != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse<>("User already exists"));
        }

        var passwordHashed = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(passwordHashed);

        var userCreated = userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(userCreated));
    }
}
