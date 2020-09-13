package com.example.controller;

import com.example.domain.dao.Task;
import com.example.domain.dto.TaskDTO;
import com.example.repository.TaskRepository;
import com.example.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest
public class TaskRestControllerTest {

    private final List<TaskDTO> tasks = new ArrayList<>();
    private final String taskId = "507f1f77bcf86cd799439011";
    private TaskDTO task1Dto;
    private TaskDTO task2Dto;
    private LocalDateTime now;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TaskService taskService;

    @MockBean
    private TaskRepository taskRepository;

    @Before
    public void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        now = LocalDateTime.now(ZoneId.of("UTC"));
        task1Dto = new TaskDTO();
        task1Dto.setTitle("Pay the electric bill");
        task1Dto.setId("507f1f77bcf86cd799439011");
        task1Dto.setCreated_at(now);

        task2Dto = new TaskDTO();
        task2Dto.setTitle("Buy a milk");
        task2Dto.setId("507f1f77bcf86cd799439012");
        task2Dto.setCreated_at(now);

        tasks.add(task1Dto);
        tasks.add(task2Dto);
    }

    @Test
    public void givenTasks_whenGetAllTasks_thenReturnJsonArray() throws Exception {
        given(taskService.listAllTasks()).willReturn(tasks);
        mvc.perform(get("/tasks/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is(task1Dto.getTitle())));
    }

    @Test
    public void givenTask_whenFindByTaskId_thenReturnJsonObject() throws Exception {
        given(taskService.findTaskById(task1Dto.getId())).willReturn(task1Dto);
        mvc.perform(get("/tasks/{taskId}", task1Dto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(task1Dto.getTitle())));
    }

    @Test
    public void saveTask_itShouldReturnStatusOk() throws Exception {
        given(taskService.saveTask(any(TaskDTO.class))).willReturn(task1Dto);
        String jsonString = objectMapper.writeValueAsString(task1Dto);
        mvc.perform(post("/tasks/")
                .contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isCreated());
    }

    @Test
    public void deleteTaskByTaskId_itShouldReturnStatusOk() throws Exception {
        given(taskRepository.deleteTaskById(task1Dto.getId())).willReturn((long) 1);
        mvc.perform(delete("/tasks/{taskId}", task1Dto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
