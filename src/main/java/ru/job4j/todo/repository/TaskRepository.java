package ru.job4j.todo.repository;

import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    Optional<Task> findById(Integer id);

    Task save(Task task);

    boolean deleteById(Integer id);

    boolean update(Task task);

    List<Task> findAll();

    List<Task> findByDone(boolean done);
}
