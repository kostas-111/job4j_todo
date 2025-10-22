package ru.job4j.todo.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repository.PriorityRepository;
import ru.job4j.todo.service.PriorityService;

@Service
public class PriorityServiceImpl implements PriorityService {

  private final PriorityRepository priorityRepository;

  public PriorityServiceImpl(PriorityRepository priorityRepository) {
    this.priorityRepository = priorityRepository;
  }

  @Override
  public Optional<Priority> findById(Integer id) {
    return priorityRepository.findById(id);
  }

  @Override
  public List<Priority> findAll() {
    return priorityRepository.findAll();
  }
}
