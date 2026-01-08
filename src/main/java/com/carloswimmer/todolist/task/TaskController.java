package com.carloswimmer.todolist.task;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/")
    public ResponseEntity<TaskModel> create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        var idUser = (UUID) request.getAttribute("idUser");
        var taskCreated = taskService.create(taskModel, idUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);
    }

    @GetMapping("/")
    public ResponseEntity<List<TaskModel>> list(HttpServletRequest request) {
        var idUser = (UUID) request.getAttribute("idUser");
        var tasks = taskService.listByUser(idUser);

        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskModel> update(@RequestBody TaskModel taskModel,
            @PathVariable UUID id,
            HttpServletRequest request) {
        var idUser = (UUID) request.getAttribute("idUser");
        var taskUpdated = taskService.update(taskModel, id, idUser);

        return ResponseEntity.status(HttpStatus.OK).body(taskUpdated);
    }

}
