package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Optional<Task> findById(Integer id);

    List<Task> findAll();

    List<Task> findCompleted();

    List<Task> findNew();

    void save(Task task);

    void deleteById(Integer id);

    void update(Task task);

    void markAsCompleted(Integer id);
}
