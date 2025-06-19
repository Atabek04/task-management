package com.atabek.taskcrud.model.dto;

import com.atabek.taskcrud.model.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response object containing task details")
public class TaskResponseDto {

    @Schema(
            description = "Unique identifier of the task",
            example = "123e4567-e89b-12d3-a456-426614174000"
    )
    private UUID id;

    @Schema(
            description = "Title of the task",
            example = "Implement user authentication"
    )
    private String title;

    @Schema(
            description = "Detailed description of the task",
            example = "Add JWT-based authentication with role-based access control and password encryption"
    )
    private String description;

    @Schema(
            description = "Current status of the task",
            example = "IN_PROGRESS",
            allowableValues = {"PENDING", "IN_PROGRESS", "COMPLETED"}
    )
    private TaskStatus status;

    @Schema(
            description = "Date and time when the task was created",
            example = "2023-08-15T14:30:00"
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Date and time when the task was last updated",
            example = "2023-08-16T09:15:00"
    )
    private LocalDateTime updatedAt;
}