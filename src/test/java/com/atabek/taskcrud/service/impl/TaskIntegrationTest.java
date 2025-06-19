package com.atabek.taskcrud.service.impl;

import com.atabek.taskcrud.model.TaskStatus;
import com.atabek.taskcrud.model.dto.TaskRequestDto;
import com.atabek.taskcrud.model.dto.TaskResponseDto;
import com.atabek.taskcrud.repository.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TaskIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskRepository taskRepository;

    @AfterEach
    void cleanup() {
        taskRepository.deleteAll();
    }

    @Test
    void taskCrudOperations() throws Exception {
        TaskRequestDto createDto = TaskRequestDto.builder()
                .title("Integration Test Task")
                .description("Testing the full API flow")
                .status(TaskStatus.PENDING)
                .build();

        MvcResult createResult = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.title", is(createDto.getTitle())))
                .andExpect(jsonPath("$.description", is(createDto.getDescription())))
                .andExpect(jsonPath("$.status", is(createDto.getStatus().toString())))
                .andExpect(jsonPath("$.createdAt", notNullValue()))
                .andExpect(jsonPath("$.updatedAt", notNullValue()))
                .andReturn();

        TaskResponseDto createdTask = objectMapper.readValue(
                createResult.getResponse().getContentAsString(),
                TaskResponseDto.class);

        UUID taskId = createdTask.getId();
        assertNotNull(taskId);

        mockMvc.perform(get("/tasks/{id}", taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(taskId.toString())))
                .andExpect(jsonPath("$.title", is(createDto.getTitle())));

        TaskRequestDto updateDto = TaskRequestDto.builder()
                .title("Updated Integration Test Task")
                .description("Updated description")
                .status(TaskStatus.IN_PROGRESS)
                .build();

        mockMvc.perform(put("/tasks/{id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(taskId.toString())))
                .andExpect(jsonPath("$.title", is(updateDto.getTitle())))
                .andExpect(jsonPath("$.description", is(updateDto.getDescription())))
                .andExpect(jsonPath("$.status", is(updateDto.getStatus().toString())));

        mockMvc.perform(delete("/tasks/{id}", taskId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/tasks/{id}", taskId))
                .andExpect(status().isNotFound());
    }

    @Test
    void createTask_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        TaskRequestDto invalidDto = TaskRequestDto.builder()
                .title("")  // Empty title
                .build();

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("Bad Request")));
    }
}