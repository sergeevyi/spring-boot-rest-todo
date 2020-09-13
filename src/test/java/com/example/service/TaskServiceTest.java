package com.example.service;

import com.example.domain.dao.Task;
import com.example.domain.dto.TaskDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import com.example.repository.TaskRepository;
import com.example.service.impl.TaskServiceImpl;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
public class TaskServiceTest {

    private final List<Task> tasks = new ArrayList<>();
    private final String taskId = "507f1f77bcf86cd799439011";
    private Task task1;
    private Task task2;
    private TaskDTO task1Dto;
    private LocalDateTime now;
    private TaskServiceImpl taskService;

    @Mock
    private static TaskRepository taskRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        taskService = new TaskServiceImpl(taskRepository);
        now = LocalDateTime.now(ZoneId.of("UTC"));
        task1 = new Task();
        task1.setTitle("Pay the electric bill");
        task1.setId("507f1f77bcf86cd799439011");
        task1.setCreated_at(now);

        task2 = new Task();
        task2.setTitle("Buy a milk");
        task2.setId("507f1f77bcf86cd799439012");
        task2.setCreated_at(now);

        tasks.add(task1);
        tasks.add(task2);

        task1Dto = new TaskDTO();
        task1Dto.setTitle("Pay the electric bill");
        task1Dto.setId("507f1f77bcf86cd799439011");
        task1Dto.setCreated_at(now);

        when(taskRepository.findAll()).thenReturn(tasks);

        when(taskRepository.findTaskById(taskId))
                .thenReturn(task1);

        when(taskRepository.saveTask(task1)).thenReturn(task1);

        when(taskRepository.deleteTaskById(task1.getId())).thenReturn((long) 1);
    }

    @Test
    public void testFindAll_thenTaskListShouldBeReturned() {
        List<TaskDTO> foundTasks = taskService.listAllTasks();
        assertNotNull(foundTasks);
        assertEquals(2, foundTasks.size());
    }

    @Test
    public void testFindByTaskId_thenTaskShouldBeReturned() {
        TaskDTO found = taskService.findTaskById(taskId);
        assertNotNull(found);
        assertEquals(task1.getTitle(), found.getTitle());
        assertEquals(task1.getId(), found.getId());
    }

    @Test
    public void testDeleteTaskById() {
        taskService.deleteTaskById(task1.getId());
        verify(taskRepository, times(1)).deleteTaskById(task1.getId());
    }

    @Test
    public void testSaveTask_thenTaskShouldBeReturned() {
        TaskDTO found = taskService.saveTask(task1Dto);
        assertNotNull(found);
        assertEquals(task1.getTitle(), found.getTitle());
        assertEquals(task1.getId(), found.getId());
    }

}
