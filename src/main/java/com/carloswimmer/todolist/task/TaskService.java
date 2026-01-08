package com.carloswimmer.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carloswimmer.todolist.exceptions.AccessDeniedException;
import com.carloswimmer.todolist.exceptions.EntityNotFoundException;
import com.carloswimmer.todolist.exceptions.InvalidTaskDateException;
import com.carloswimmer.todolist.utils.ObjectMerger;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public TaskModel create(TaskModel taskModel, UUID idUser) {
        var currentDate = LocalDateTime.now();

        if (currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
            throw new InvalidTaskDateException("The start date or end date must be greater than the current date");
        }

        if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
            throw new InvalidTaskDateException("The start date must be before the end date");
        }

        taskModel.setIdUser(idUser);
        return taskRepository.save(taskModel);
    }

    public List<TaskModel> listByUser(UUID idUser) {
        return taskRepository.findByIdUser(idUser);
    }

    public TaskModel update(TaskModel taskModel, UUID id, UUID idUser) {
        var task = taskRepository.findById(id).orElse(null);

        if (task == null) {
            throw new EntityNotFoundException("Task not found with id: " + id);
        }

        if (!task.getIdUser().equals(idUser)) {
            throw new AccessDeniedException("You are not authorized to update this task");
        }

        ObjectMerger.merge(taskModel, task);
        return taskRepository.save(task);
    }
}
