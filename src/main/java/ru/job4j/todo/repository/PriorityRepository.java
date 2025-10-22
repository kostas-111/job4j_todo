package ru.job4j.todo.repository;

import java.util.List;
import java.util.Optional;
import ru.job4j.todo.model.Priority;

public interface PriorityRepository {

  Optional<Priority> findById(Integer id);

  List<Priority> findAll();

}
