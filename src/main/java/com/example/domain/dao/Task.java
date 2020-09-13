package com.example.domain.dao;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "tasks")
@NoArgsConstructor
@Data
public class Task {
    @Id
    private String id;
    private String title;
    private LocalDateTime created_at;
}
