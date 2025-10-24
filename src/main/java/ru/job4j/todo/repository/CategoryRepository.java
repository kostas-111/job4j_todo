package ru.job4j.todo.repository;

import java.util.List;
import java.util.Optional;
import ru.job4j.todo.model.Category;

public interface CategoryRepository {

  Optional<Category> findById(Integer id);

  List<Category> findAllById(List<Integer> ids);

  List<Category> findAll();
}
