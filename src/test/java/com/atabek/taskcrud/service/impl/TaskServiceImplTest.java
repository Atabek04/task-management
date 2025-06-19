package com.atabek.taskcrud.service.impl;

import com.atabek.taskcrud.exception.ResourceNotFoundException;
import com.atabek.taskcrud.mapper.TaskMapper;
import com.atabek.taskcrud.model.Task;
import com.atabek.taskcrud.model.TaskStatus;
import com.atabek.taskcrud.model.dto.TaskRequestDto;
import com.atabek.taskcrud.model.dto.TaskResponseDto;
import com.atabek.taskcrud.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    private UUID taskId;
    private Task task;
    private TaskRequestDto requestDto;
    private TaskResponseDto responseDto;

    @BeforeEach
    void setUp() {
        taskId = UUID.randomUUID();

        task = Task.builder()
                .id(taskId)
                .title("Test Task")
                .description("Test Description")
                .status(TaskStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        requestDto = TaskRequestDto.builder()
                .title("Test Task")
                .description("Test Description")
                .status(TaskStatus.PENDING)
                .build();

        responseDto = TaskResponseDto.builder()
                .id(taskId)
                .title("Test Task")
                .description("Test Description")
                .status(TaskStatus.PENDING)
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }

    @Test
    void createTask_ShouldReturnCreatedTask() {
        when(taskMapper.toEntity(requestDto)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toDto(task)).thenReturn(responseDto);

        TaskResponseDto result = taskService.createTask(requestDto);

        assertNotNull(result);
        assertEquals(taskId, result.getId());
        assertEquals(requestDto.getTitle(), result.getTitle());
        assertEquals(requestDto.getDescription(), result.getDescription());
        assertEquals(requestDto.getStatus(), result.getStatus());

        verify(taskMapper).toEntity(requestDto);
        verify(taskRepository).save(task);
        verify(taskMapper).toDto(task);
    }

    @Test
    void getTaskById_WithValidId_ShouldReturnTask() {
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskMapper.toDto(task)).thenReturn(responseDto);

        TaskResponseDto result = taskService.getTaskById(taskId);

        assertNotNull(result);
        assertEquals(taskId, result.getId());

        verify(taskRepository).findById(taskId);
        verify(taskMapper).toDto(task);
    }

    @Test
    void getTaskById_WithInvalidId_ShouldThrowException() {
        UUID invalidId = UUID.randomUUID();
        when(taskRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> taskService.getTaskById(invalidId));
        verify(taskRepository).findById(invalidId);
        verify(taskMapper, never()).toDto(any());
    }

    @Test
    void updateTask_WithValidIdAndData_ShouldReturnUpdatedTask() {
        TaskRequestDto updateDto = TaskRequestDto.builder()
                .title("Updated Title")
                .description("Updated Description")
                .status(TaskStatus.IN_PROGRESS)
                .build();

        Task updatedTask = Task.builder()
                .id(taskId)
                .title("Updated Title")
                .description("Updated Description")
                .status(TaskStatus.IN_PROGRESS)
                .createdAt(task.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();

        TaskResponseDto updatedResponseDto = TaskResponseDto.builder()
                .id(taskId)
                .title("Updated Title")
                .description("Updated Description")
                .status(TaskStatus.IN_PROGRESS)
                .createdAt(task.getCreatedAt())
                .updatedAt(updatedTask.getUpdatedAt())
                .build();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        doNothing().when(taskMapper).updateEntityFromDto(updateDto, task);
        when(taskRepository.save(task)).thenReturn(updatedTask);
        when(taskMapper.toDto(updatedTask)).thenReturn(updatedResponseDto);

        TaskResponseDto result = taskService.updateTask(taskId, updateDto);

        assertNotNull(result);
        assertEquals(taskId, result.getId());
        assertEquals(updateDto.getTitle(), result.getTitle());
        assertEquals(updateDto.getDescription(), result.getDescription());
        assertEquals(updateDto.getStatus(), result.getStatus());

        verify(taskRepository).findById(taskId);
        verify(taskMapper).updateEntityFromDto(updateDto, task);
        verify(taskRepository).save(task);
        verify(taskMapper).toDto(any());
    }

    @Test
    void deleteTask_WithValidId_ShouldDeleteTask() {
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        doNothing().when(taskRepository).delete(task);

        taskService.deleteTask(taskId);

        verify(taskRepository).findById(taskId);
        verify(taskRepository).delete(task);
    }
}