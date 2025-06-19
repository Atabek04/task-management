package com.atabek.taskcrud.controller;

import com.atabek.taskcrud.controller.api.TaskApi;
import com.atabek.taskcrud.model.dto.TaskRequestDto;
import com.atabek.taskcrud.model.dto.TaskResponseDto;
import com.atabek.taskcrud.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Slf4j
public class TaskController implements TaskApi {

    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponseDto createTask(@Valid @RequestBody TaskRequestDto requestDto) {
        log.info("Received request to create task with title: {}", requestDto.getTitle());
        TaskResponseDto response = taskService.createTask(requestDto);
        log.info("Created task with ID: {}", response.getId());
        return response;
    }

    @GetMapping("/{id}")
    public TaskResponseDto getTaskById(@PathVariable UUID id) {
        log.info("Received request to get task with ID: {}", id);
        TaskResponseDto response = taskService.getTaskById(id);
        log.debug("Retrieved task: {}", response);
        return response;
    }

    @PutMapping("/{id}")
    public TaskResponseDto updateTask(
            @PathVariable UUID id,
            @Valid @RequestBody TaskRequestDto updateDto) {
        log.info("Received request to update task with ID: {}", id);
        TaskResponseDto response = taskService.updateTask(id, updateDto);
        log.info("Updated task with ID: {}", id);
        return response;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable UUID id) {
        log.info("Received request to delete task with ID: {}", id);
        taskService.deleteTask(id);
        log.info("Deleted task with ID: {}", id);
    }
}
