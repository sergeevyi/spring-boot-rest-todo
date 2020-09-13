package com.example.service;

import com.example.domain.dto.TaskDTO;
import java.util.List;

public interface TaskService {

    public List<TaskDTO> listAllTasks();

    public TaskDTO saveTask(TaskDTO task);

    public TaskDTO findTaskById(String id);

    public TaskDTO updateTask(TaskDTO task);

    public void deleteTaskById(String id);
}
