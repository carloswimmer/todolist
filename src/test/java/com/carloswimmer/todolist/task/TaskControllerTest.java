package com.carloswimmer.todolist.task;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.carloswimmer.todolist.user.UserModel;
import com.carloswimmer.todolist.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import at.favre.lib.crypto.bcrypt.BCrypt;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @MockitoBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID userId;
    private String authHeader;
    private UUID taskId;

    private TaskModel createValidTask() {
        TaskModel task = new TaskModel();
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setStartAt(LocalDateTime.now().plusHours(1));
        task.setEndAt(LocalDateTime.now().plusHours(2));
        task.setPriority("High");
        return task;
    }

    private TaskModel createTaskFromDatabase() {
        TaskModel task = createValidTask();
        task.setId(taskId);
        task.setIdUser(userId);
        return task;
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userId = UUID.randomUUID();
        taskId = UUID.randomUUID();
        String username = "testuser";
        String password = "password123";

        String auth = username + ":" + password;
        String authEncoded = Base64.getEncoder().encodeToString(auth.getBytes());
        authHeader = "Basic " + authEncoded;

        UserModel user = new UserModel();
        user.setId(userId);
        user.setUsername(username);
        user.setPassword(BCrypt.withDefaults().hashToString(12, password.toCharArray()));

        when(userRepository.findByUsername(username)).thenReturn(user);
    }

    @Test
    void shouldReturn201WhenTaskIsCreated() throws Exception {
        TaskModel task = createValidTask();

        when(taskService.create(any(TaskModel.class), eq(userId))).thenReturn(task);

        mockMvc.perform(post("/tasks/")
                .header("Authorization", authHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn200WhenTasksAreListed() throws Exception {
        TaskModel task1 = createTaskFromDatabase();
        TaskModel task2 = createTaskFromDatabase();
        task2.setId(UUID.randomUUID());

        when(taskService.listByUser(eq(userId))).thenReturn(List.of(task1, task2));

        mockMvc.perform(get("/tasks/")
                .header("Authorization", authHeader)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn200WhenTaskIsUpdated() throws Exception {
        TaskModel task = createTaskFromDatabase();

        when(taskService.update(any(TaskModel.class), eq(taskId), eq(userId))).thenReturn(task);

        mockMvc.perform(put("/tasks/{id}", taskId)
                .header("Authorization", authHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)));
    }

}
