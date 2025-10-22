package ru.job4j.todo.service;

import java.util.List;
import java.util.Optional;
import ru.job4j.todo.model.Priority;

public interface PriorityService {

  Optional<Priority> findById(Integer id);

  List<Priority> findAll();
}
