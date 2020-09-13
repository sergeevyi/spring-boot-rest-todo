package com.example.controller;

import com.example.domain.dto.TaskDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Get all tasks")
    @RequestMapping( method = RequestMethod.GET)
    public ResponseEntity<List<TaskDTO>> list(){
        List<TaskDTO> tasksDto = taskService.listAllTasks();
        return new ResponseEntity<>(tasksDto, HttpStatus.OK);
    }

    @Operation(summary = "Get a task by its id")
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ResponseEntity<TaskDTO> getTaskById(@Parameter(description = "id of task to be searched") @PathVariable String id) {
        TaskDTO taskDto = taskService.findTaskById(id);
        return new ResponseEntity<>(taskDto, HttpStatus.OK);
    }

    @Operation(summary = "Update a task")
    @RequestMapping( method = RequestMethod.PUT)
    public ResponseEntity<TaskDTO> updateTask(@RequestBody TaskDTO taskDto) {
        TaskDTO updatedTaskDto = taskService.updateTask(taskDto);
        return new ResponseEntity<>(updatedTaskDto, HttpStatus.OK);
    }


    @Operation(summary = "Create a new task")
    @RequestMapping( method = RequestMethod.POST)
    public ResponseEntity<TaskDTO> addTask(@RequestBody TaskDTO taskDto) {
        TaskDTO savedTaskDto= taskService.saveTask(taskDto);
        return new ResponseEntity<>(savedTaskDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a task by its id")
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<TaskDTO> deleteByTaskId(@PathVariable String id)  {
        taskService.deleteTaskById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
