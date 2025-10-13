package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.impl.TaskServiceImpl;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskServiceImpl taskService;

    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String getAll(@RequestParam(defaultValue = "all") String filter, Model model) {

        /*
        Реализовал Map c фильтрами.
        Теперь достаточно добавить фильтр сюда, чтобы он появился
         */
        Map<String, Supplier<List<Task>>> filters = Map.of(
            "completed", taskService::findCompleted,
            "new", taskService::findNew,
            "all", taskService::findAll
        );
        Supplier<List<Task>> strategy = filters.getOrDefault(filter, taskService::findAll);
        List<Task> tasks = strategy.get();

        model.addAttribute("tasks", tasks);
        model.addAttribute("currentFilter", filter);
        return "tasks/list";
    }

    @GetMapping("/create")
    public String getCreationPage() {
        return "tasks/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task) {
        taskService.save(task);
        return "redirect:/tasks";

    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        Task task = taskService.findById(id).get();
        model.addAttribute("tasks", taskService.findAll());
        model.addAttribute("task", task);
        return "tasks/one";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        taskService.deleteById(id);
        return "redirect:/tasks";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePage(Model model, @PathVariable int id) {
        var vacancyOptional = taskService.findById(id);
        model.addAttribute("task", vacancyOptional.get());
        return "tasks/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Task task) {
        taskService.update(task);
        return "redirect:/tasks";
    }

    @GetMapping("/complete/{id}")
    public String completeTask(@PathVariable Integer id) {
        taskService.markAsCompleted(id);
        return "redirect:/tasks";
    }
}
