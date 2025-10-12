package ru.job4j.todo.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String description;

    @Column(columnDefinition = "TIMESTAMP DEFAULT NOW()", insertable = false)
    private LocalDateTime created;

    @Column(columnDefinition = "boolean default false")
    private boolean done;
}
