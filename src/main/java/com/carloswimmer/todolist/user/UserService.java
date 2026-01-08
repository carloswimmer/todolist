package com.carloswimmer.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.carloswimmer.todolist.exceptions.UserAlreadyExistsException;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserModel create(UserModel userModel) {
        var user = userRepository.findByUsername(userModel.getUsername());

        if (user != null) {
            throw new UserAlreadyExistsException();
        }

        var passwordHashed = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(passwordHashed);

        return userRepository.save(userModel);
    }

}
