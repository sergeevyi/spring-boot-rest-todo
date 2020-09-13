package com.example.repository.impl;

import com.mongodb.client.result.DeleteResult;
import com.example.domain.dao.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.example.repository.TaskRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<Task> findAll() {
        List<Task> tasks = mongoTemplate.find(new Query(), Task.class);
        return tasks;
    }

    @Override
    public Task saveTask(Task task) {
        Task savedTask = mongoTemplate.save(task);
        return savedTask;
    }

    @Override
    public Task findTaskById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Task task = mongoTemplate.findOne(query, Task.class);
        return task;
    }

    @Override
    public Task updateTask(Task task) {
        Query query = new Query(Criteria.where("_id").is(task.getId()));
        Update update = new Update();
        update.set("title", task.getTitle());
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);
        return mongoTemplate.findAndModify(query, update, options, Task.class);
    }

    @Override
    public long deleteTaskById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        DeleteResult result = mongoTemplate.remove(query,Task.class);
        return result.getDeletedCount();
    }
}
