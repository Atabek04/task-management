package com.atabek.taskcrud.service;

import com.atabek.taskcrud.exception.ResourceNotFoundException;
import com.atabek.taskcrud.model.dto.TaskRequestDto;
import com.atabek.taskcrud.model.dto.TaskResponseDto;

import java.util.UUID;

/**
 * Service for managing task operations.
 * Provides CRUD functionality for tasks with validation and error handling.
 */
public interface TaskService {

    /**
     * Creates a new task based on the provided data.
     *
     * @param requestDto Data for creating the task
     * @return The created task with generated ID and timestamps
     */
    TaskResponseDto createTask(TaskRequestDto requestDto);

    /**
     * Retrieves a task by its unique identifier.
     *
     * @param id The UUID of the task to retrieve
     * @return The task data
     * @throws ResourceNotFoundException if no task with the given ID exists
     */
    TaskResponseDto getTaskById(UUID id);

    /**
     * Updates an existing task with new data.
     * Only provided fields will be updated; null fields are ignored.
     *
     * @param id The UUID of the task to update
     * @param updateDto The data to update the task with
     * @return The updated task data
     * @throws ResourceNotFoundException if no task with the given ID exists
     */
    TaskResponseDto updateTask(UUID id, TaskRequestDto updateDto);

    /**
     * Deletes a task permanently.
     *
     * @param id The UUID of the task to delete
     * @throws ResourceNotFoundException if no task with the given ID exists
     */
    void deleteTask(UUID id);
}