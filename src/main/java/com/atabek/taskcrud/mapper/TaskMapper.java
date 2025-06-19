package com.atabek.taskcrud.mapper;

import com.atabek.taskcrud.model.Task;
import com.atabek.taskcrud.model.dto.TaskRequestDto;
import com.atabek.taskcrud.model.dto.TaskResponseDto;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task toEntity(TaskRequestDto dto) {
        return Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .build();
    }

    public TaskResponseDto toDto(Task entity) {
        return TaskResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public void updateEntityFromDto(TaskRequestDto dto, Task entity) {
        if (dto.getTitle() != null) {
            entity.setTitle(dto.getTitle());
        }

        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }

        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
    }
}
