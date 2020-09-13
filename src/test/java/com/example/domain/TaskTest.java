package com.example.domain;

import com.example.domain.dao.Task;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TaskTest {

    Task task;

    @Before
    public void setUp(){
        task = new Task();
    }

    @Test
    public void getId() throws Exception {
        String title = "Buy a milk";
        task.setTitle(title);
        assertEquals(title, task.getTitle());
    }
}
