package ru.job4j.todo.service.impl;

import exception.TaskNotFoundException;
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
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new TaskNotFoundException(id);
        }
        return task;
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
    public void deleteById(Integer id) {
        boolean isUpdated = taskRepository.deleteById(id);
        if (!isUpdated) {
            throw new TaskNotFoundException(id);
        }
    }

    @Override
    public void update(Task task) {
        boolean isUpdated = taskRepository.update(task);
        if (!isUpdated) {
            throw new TaskNotFoundException(task.getId());
        }
    }

    @Override
    public void markAsCompleted(Integer id) {
        boolean isUpdated = taskRepository.markAsCompleted(id);
        if (!isUpdated) {
            throw new TaskNotFoundException(id);
        }
    }
}
