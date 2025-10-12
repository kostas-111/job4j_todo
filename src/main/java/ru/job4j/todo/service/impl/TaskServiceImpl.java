package ru.job4j.todo.service.impl;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.impl.HbmTaskRepository;
import ru.job4j.todo.service.TaskService;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final HbmTaskRepository taskRepository;

    public TaskServiceImpl(HbmTaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Optional<Task> findById(Integer id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> findCompleted() {
        return taskRepository.findByDone(true);
    }

    @Override
    public List<Task> findNew() {
        return taskRepository.findByDone(false);
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public boolean deleteById(Integer id) {
        return taskRepository.deleteById(id);
    }

    @Override
    public boolean update(Task task) {
        return taskRepository.update(task);
    }

    @Override
    public void markAsCompleted(Integer id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setDone(true);
            taskRepository.update(task);
        }
    }
}
