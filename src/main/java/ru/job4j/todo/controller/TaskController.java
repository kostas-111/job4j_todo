package ru.job4j.todo.controller;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.impl.PriorityServiceImpl;
import ru.job4j.todo.service.impl.TaskServiceImpl;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskServiceImpl taskService;
    private final PriorityServiceImpl priorityService;
    private final Map<String, Supplier<List<Task>>> filters;

    public TaskController(TaskServiceImpl taskService, PriorityServiceImpl priorityService) {
        this.taskService = taskService;
        this.filters = Map.of(
            "completed", taskService::findCompleted,
            "new", taskService::findNew,
            "all", taskService::findAll
        );
      this.priorityService = priorityService;
    }

    @GetMapping
    public String getAll(@RequestParam(defaultValue = "all") String filter, Model model) {

        Supplier<List<Task>> strategy = filters.getOrDefault(filter, taskService::findAll);
        List<Task> tasks = strategy.get();

        model.addAttribute("tasks", tasks);
        model.addAttribute("currentFilter", filter);
        return "tasks/list";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        model.addAttribute("priorities", priorityService.findAll());
        return "tasks/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task,
                         @RequestParam Integer priorityId,
                         HttpSession session) {

        User currentUser = (User) session.getAttribute("user");
        task.setUser(currentUser);

        var priority = priorityService.findById(priorityId);
        task.setPriority(priority.get());

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
        model.addAttribute("priorities", priorityService.findAll());
        return "tasks/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Task task,
                         @RequestParam Integer priorityId) {

        var priority = priorityService.findById(priorityId);
        task.setPriority(priority.get());
        taskService.update(task);
        return "redirect:/tasks";
    }

    @GetMapping("/complete/{id}")
    public String completeTask(@PathVariable Integer id) {
        taskService.markAsCompleted(id);
        return "redirect:/tasks";
    }
}
