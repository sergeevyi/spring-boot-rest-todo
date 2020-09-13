package com.example.service.impl;

import com.example.domain.dao.Task;
import com.example.domain.dto.TaskDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import com.example.repository.TaskRepository;
import com.example.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    ModelMapper modelMapper = new ModelMapper();

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<TaskDTO> listAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        if(tasks == null)
            tasks = new ArrayList<>();
        // Create Conversion Type
        Type listType = new TypeToken<List<TaskDTO>>() {}.getType();
        // Convert List of Entity objects to a List of DTOs objects
        List<TaskDTO> tasksDTO = modelMapper.map(tasks, listType);
        return tasksDTO;
    }

    @Override
    public TaskDTO saveTask(TaskDTO taskDTO) {
        Task task = modelMapper.map(taskDTO, Task.class);
        if(taskDTO.getCreated_at() == null)
            task.setCreated_at(LocalDateTime.now(ZoneId.of("UTC")));
        Task savedTask = taskRepository.saveTask(task);
        if (savedTask == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Failed to create a task.");
        }
        TaskDTO savedTaskDTO = modelMapper.map(savedTask, TaskDTO.class);
        return savedTaskDTO;
    }

    @Override
    public TaskDTO findTaskById(String id) {
        Task task = taskRepository.findTaskById(id);
        TaskDTO taskDTO = modelMapper.map(task, TaskDTO.class);
        if (taskDTO == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Could not delete task with this id");
        }
        return taskDTO;
    }

    @Override
    public TaskDTO updateTask(TaskDTO taskDTO) {
        Task updateTask = modelMapper.map(taskDTO, Task.class);
        Task updatedTask =  taskRepository.updateTask(updateTask);
        if (updatedTask == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Could not delete task with this id");
        }
        TaskDTO updatedTaskDTO = modelMapper.map(updatedTask, TaskDTO.class);
        return updatedTaskDTO;
    }

    @Override
    public void deleteTaskById(String id)  {
        long deletedCount = taskRepository.deleteTaskById(id);
        if(deletedCount == 0)
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Could not delete task with this id");

    }
}
