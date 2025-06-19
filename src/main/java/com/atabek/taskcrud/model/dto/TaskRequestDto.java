package com.atabek.taskcrud.model.dto;

import com.atabek.taskcrud.model.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request DTO for creating or updating a task")
public class TaskRequestDto {

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must be less than 100 characters")
    @Schema(
            description = "Title of the task (required, max 100 characters)",
            example = "Implement user authentication",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String title;

    @Schema(
            description = "Detailed description of the task (optional)",
            example = "Add JWT-based authentication with role-based access control and password encryption."
    )
    private String description;

    @Schema(
            description = "Current status of the task (default is PENDING)",
            example = "IN_PROGRESS",
            allowableValues = {"PENDING", "IN_PROGRESS", "COMPLETED"}
    )
    private TaskStatus status;
}
