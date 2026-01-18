package com.carloswimmer.todolist.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.carloswimmer.todolist.exceptions.UserAlreadyExistsException;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateUserWithSuccess() {
        UserModel user = new UserModel();
        user.setUsername("testuser");
        user.setPassword("password123");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(null);
        when(userRepository.save(any(UserModel.class))).thenReturn(user);

        UserModel result = userService.create(user);

        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getPassword(), result.getPassword());
    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyExists() {
        UserModel user = new UserModel();
        user.setUsername("existinguser");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(new UserModel());

        assertThrows(UserAlreadyExistsException.class, () -> userService.create(user));
    }
}
