package ru.job4j.todo.repository.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CategoryRepository;
import ru.job4j.todo.utils.HibernateTransactionHelper;

@Repository
public class HbmCategoryRepository implements CategoryRepository {

  private final HibernateTransactionHelper transactionHelper;

  public HbmCategoryRepository(HibernateTransactionHelper transactionHelper) {
    this.transactionHelper = transactionHelper;
  }

  @Override
  public Optional<Category> findById(Integer id) {
    return transactionHelper.optional(
        "FROM Category WHERE id = :fId", Category.class,
        Map.of("fId", id)
    );
  }

  @Override
  public List<Category> findAllById(List<Integer> ids) {
    return transactionHelper.query(
        "FROM Category WHERE id in :fId", Category.class,
        Map.of("fId", ids)
    );
  }

  @Override
  public List<Category> findAll() {
    return transactionHelper.query("FROM Category ORDER BY id", Category.class);
  }
}
