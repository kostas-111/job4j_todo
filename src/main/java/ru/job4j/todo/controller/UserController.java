package ru.job4j.todo.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TimeZone;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/register")
	public String getRegistrationPage(Model model) {

		/*
		Получаем все доступные часовые пояса
		 */
		List<TimeZone> timezones = new ArrayList<>();
		for (String timeId : TimeZone.getAvailableIDs()) {
			timezones.add(TimeZone.getTimeZone(timeId));
		}
		timezones.sort(Comparator.comparing(TimeZone::getID));

		/*
		Часовой пояс по умолчанию
		 */
		TimeZone defaultTimezone = TimeZone.getDefault();

		model.addAttribute("timezones", timezones);
		model.addAttribute("defaultTimezone",
				defaultTimezone.getID() + " : " + defaultTimezone.getDisplayName());

		return "users/register";
	}

	@PostMapping("/register")
	public String register(@ModelAttribute User user) {
		userService.save(user);
		return "redirect:/tasks";
	}

	@GetMapping("/login")
	public String getLoginPage() {
		return "users/login";
	}

	@PostMapping("/login")
	public String loginUser(@ModelAttribute User user, HttpServletRequest request) {
		Optional<User> userOptional = userService.findByLoginAndPassword(user.getLogin(), user.getPassword());
		var session = request.getSession();
		session.setAttribute("user", userOptional.get());
		return "redirect:/tasks";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/users/login";
	}
}
