package com.carloswimmer.todolist.task;

import java.time.LocalDateTime;
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

import com.carloswimmer.todolist.ApiResponse;
import com.carloswimmer.todolist.dto.ErrorResponse;
import com.carloswimmer.todolist.dto.SuccessResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity<ApiResponse<TaskModel>> create(@RequestBody TaskModel taskModel, HttpServletRequest request) {

        var currentDate = LocalDateTime.now();
        if (currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse<>("The start date or end date must be greater than the current date"));
        }

        if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse<>("The start date must be before the end date"));
        }

        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID) idUser);
        var taskCreated = taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(taskCreated));

    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<TaskModel>>> list(HttpServletRequest request) {
        var idUser = request.getAttribute("idUser");
        var tasks = taskRepository.findByIdUser((UUID) idUser);

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(tasks));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskModel>> update(@RequestBody TaskModel taskModel, @PathVariable UUID id,
            HttpServletRequest request) {

        var idUser = request.getAttribute("idUser");

        taskModel.setIdUser((UUID) idUser);
        taskModel.setId(id);

        var taskUpdated = taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(taskUpdated));

    }
}
