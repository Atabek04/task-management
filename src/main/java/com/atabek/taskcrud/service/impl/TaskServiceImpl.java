package com.atabek.taskcrud.service.impl;

import com.atabek.taskcrud.exception.ResourceNotFoundException;
import com.atabek.taskcrud.mapper.TaskMapper;
import com.atabek.taskcrud.model.Task;
import com.atabek.taskcrud.model.dto.TaskRequestDto;
import com.atabek.taskcrud.model.dto.TaskResponseDto;
import com.atabek.taskcrud.repository.TaskRepository;
import com.atabek.taskcrud.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    @Transactional
    public TaskResponseDto createTask(TaskRequestDto requestDto) {
        Task task = taskMapper.toEntity(requestDto);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toDto(savedTask);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponseDto getTaskById(UUID id) {
        Task task = findTaskById(id);
        return taskMapper.toDto(task);
    }

    @Override
    @Transactional
    public TaskResponseDto updateTask(UUID id, TaskRequestDto updateDto) {
        Task task = findTaskById(id);
        taskMapper.updateEntityFromDto(updateDto, task);
        Task updatedTask = taskRepository.save(task);
        return taskMapper.toDto(updatedTask);
    }

    @Override
    @Transactional
    public void deleteTask(UUID id) {
        Task task = findTaskById(id);
        taskRepository.delete(task);
    }

    private Task findTaskById(UUID id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }
}