package com.example.repository;

import com.example.domain.dao.Task;

import java.util.List;

public interface TaskRepository {

    public List<Task> findAll();

    public Task saveTask(Task user);

    public Task findTaskById(String taskId);

    public Task updateTask(Task task);

    public long deleteTaskById(String taskId);

}
