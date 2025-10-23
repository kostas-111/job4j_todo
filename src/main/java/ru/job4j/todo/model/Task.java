package ru.job4j.todo.model;

import java.util.ArrayList;
import java.util.List;
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

    private String description;

    @Column(columnDefinition = "TIMESTAMP DEFAULT NOW()", insertable = false)
    private LocalDateTime created;

    @Column(columnDefinition = "boolean default false")
    private boolean done;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "priority_id")
    private Priority priority;

    @ManyToMany
    @JoinTable(
        name = "tasks_categories_mapping",
        joinColumns = { @JoinColumn(name = "task_id") },
        inverseJoinColumns = { @JoinColumn(name = "category_id") }
    )
    private List<Category> categories = new ArrayList<>();
}