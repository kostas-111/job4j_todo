package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.impl.TaskServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskServiceImpl taskService;

    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String getAll(@RequestParam(defaultValue = "all") String filter, Model model) {
        List<Task> tasks;
        switch (filter) {
            case "completed":
                tasks = taskService.findCompleted();
                break;
            case "new":
                tasks = taskService.findNew();
                break;
            case "all":
            default:
                tasks = taskService.findAll();
                break;
        }
        model.addAttribute("tasks", tasks);
        model.addAttribute("currentFilter", filter);
        return "tasks/list";
    }

    @GetMapping("/create")
    public String getCreationPage() {
        return "tasks/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, Model model) {
        try {
            taskService.save(task);
            return "redirect:/tasks";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var vacancyOptional = taskService.findById(id);
        if (vacancyOptional.isEmpty()) {
            model.addAttribute("message", "Задание с указанным идентификатором не найдено");
            return "errors/404";
        }
        model.addAttribute("tasks", taskService.findAll());
        model.addAttribute("task", vacancyOptional.get());
        return "tasks/one";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        var isDeleted = taskService.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "Задание с указанным идентификатором не найдено");
            return "errors/404";
        }
        return "redirect:/tasks";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePage(Model model, @PathVariable int id) {
        var vacancyOptional = taskService.findById(id);
        if (vacancyOptional.isEmpty()) {
            model.addAttribute("message", "Задание с указанным идентификатором не найдено");
            return "errors/404";
        }
        model.addAttribute("task", vacancyOptional.get());
        return "tasks/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Task task, Model model) {
        try {
            boolean isUpdated = taskService.update(task);
            if (!isUpdated) {
                model.addAttribute("message", "Задание с указанным идентификатором не найдено");
                return "errors/404";
            }
            return "redirect:/tasks";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/complete/{id}")
    public String completeTask(@PathVariable Integer id) {
        taskService.markAsCompleted(id);
        return "redirect:/tasks";
    }
}
