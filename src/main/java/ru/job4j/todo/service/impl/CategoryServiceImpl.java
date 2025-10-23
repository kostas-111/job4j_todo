package ru.job4j.todo.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CategoryRepository;
import ru.job4j.todo.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  public CategoryServiceImpl(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public Optional<Category> findById(Integer id) {
    return categoryRepository.findById(id);
  }

  @Override
  public List<Category> findAll() {
    return categoryRepository.findAll();
  }
}
