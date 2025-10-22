package ru.job4j.todo.repository.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repository.PriorityRepository;
import ru.job4j.todo.utils.HibernateTransactionHelper;

@Repository
public class HbmPriorityRepository implements PriorityRepository {

  private final HibernateTransactionHelper transactionHelper;

  public HbmPriorityRepository(HibernateTransactionHelper transactionHelper) {
    this.transactionHelper = transactionHelper;
  }

  @Override
  public Optional<Priority> findById(Integer id) {
    return transactionHelper.optional(
        "FROM Priority WHERE id = :fId", Priority.class,
        Map.of("fId", id)
    );
  }

  @Override
  public List<Priority> findAll() {
    return transactionHelper.query("FROM Priority ORDER BY position", Priority.class);
  }
}
