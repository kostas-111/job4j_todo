package ru.job4j.todo.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @EqualsAndHashCode.Include
    private String title;

    @EqualsAndHashCode.Include
    private String description;

    @Column(columnDefinition = "TIMESTAMP DEFAULT NOW()", insertable = false)
    private LocalDateTime created;

    @Column(columnDefinition = "boolean default false")
    @EqualsAndHashCode.Include
    private boolean done;
}
