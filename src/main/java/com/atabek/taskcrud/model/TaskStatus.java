package com.atabek.taskcrud.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Status of a task")
public enum TaskStatus {
    @Schema(description = "Task is waiting to be started")
    PENDING,

    @Schema(description = "Task is currently being worked on")
    IN_PROGRESS,

    @Schema(description = "Task has been finished")
    COMPLETED
}
