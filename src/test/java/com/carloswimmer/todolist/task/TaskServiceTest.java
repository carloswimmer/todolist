package com.carloswimmer.todolist.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.carloswimmer.todolist.exceptions.AccessDeniedException;
import com.carloswimmer.todolist.exceptions.BusinessException;
import com.carloswimmer.todolist.exceptions.EntityNotFoundException;
import com.carloswimmer.todolist.exceptions.InvalidTaskDateException;

public class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    private UUID userId;
    private UUID taskId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userId = UUID.randomUUID();
        taskId = UUID.randomUUID();
    }

    private TaskModel createValidTask() {
        TaskModel task = new TaskModel();
        task.setId(taskId);
        task.setIdUser(userId);
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setStartAt(LocalDateTime.now().plusHours(1));
        task.setEndAt(LocalDateTime.now().plusHours(2));
        task.setPriority("High");
        return task;
    }

    @Test
    void shouldCreateTaskWithSuccess() {
        TaskModel task = createValidTask();

        when(taskRepository.save(any(TaskModel.class))).thenReturn(task);

        TaskModel result = taskService.create(task, UUID.randomUUID());

        assertNotNull(result);
        assertEquals(task.getTitle(), result.getTitle());
        assertEquals(task.getDescription(), result.getDescription());
        assertEquals(task.getStartAt(), result.getStartAt());
        assertEquals(task.getEndAt(), result.getEndAt());
        assertEquals(task.getPriority(), result.getPriority());
    }

    @Test
    void shouldThrowExceptionWhenTitleIsTooLong() {
        TaskModel task = createValidTask();
        task.setTitle("This is a title that is too long and should throw an exception");

        assertThrows(BusinessException.class, () -> taskService.create(task, UUID.randomUUID()));
    }

    @Test
    void shouldThrowExceptionWhenStartDateIsInThePast() {
        TaskModel task = createValidTask();
        task.setStartAt(LocalDateTime.now().minusHours(1));

        assertThrows(InvalidTaskDateException.class, () -> taskService.create(task, UUID.randomUUID()));
    }

    @Test
    void shouldThrowExceptionWhenStartDateIsAfterEndDate() {
        TaskModel task = createValidTask();
        task.setStartAt(LocalDateTime.now().plusHours(3));

        assertThrows(InvalidTaskDateException.class, () -> taskService.create(task, UUID.randomUUID()));
    }

    @Test
    void shouldUpdateTaskWithSuccess() {
        TaskModel taskInDb = createValidTask();
        TaskModel updatedRequest = new TaskModel();
        updatedRequest.setTitle("New Title");

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskInDb));
        when(taskRepository.save(any(TaskModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskModel result = taskService.update(updatedRequest, taskId, userId);

        assertNotNull(result);
        assertEquals(updatedRequest.getTitle(), result.getTitle());
        assertEquals(taskInDb.getDescription(), result.getDescription());
        assertEquals(taskInDb.getStartAt(), result.getStartAt());
        assertEquals(taskInDb.getEndAt(), result.getEndAt());
        assertEquals(taskInDb.getPriority(), result.getPriority());
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFound() {

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.update(new TaskModel(), taskId, userId));
    }

    @Test
    void shouldThrowExceptionWhenUserNotAuthorized() {
        UUID unauthorizedUserId = UUID.randomUUID();

        TaskModel taskInDb = createValidTask();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskInDb));

        assertThrows(AccessDeniedException.class,
                () -> taskService.update(new TaskModel(), taskId, unauthorizedUserId));
    }
}
