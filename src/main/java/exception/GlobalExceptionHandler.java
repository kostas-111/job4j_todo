package exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Глобальный обработчик исключений для Spring MVC контроллеров.
 *
 * Этот класс перехватывает исключения, возникающие в любом контроллере приложения,
 * и преобразует их в пользовательские HTTP-ответы с соответствующими страницами ошибок.
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(TaskNotFoundException.class)
	public String handleTaskNotFound(TaskNotFoundException exception, Model model) {
		model.addAttribute("message", exception.getMessage());
		return "errors/404";
	}

	@ExceptionHandler(Exception.class)
	public String handleGeneralException(Exception exception, Model model) {
		model.addAttribute("message", "Произошла ошибка: " + exception.getMessage());
		return "errors/500";
	}
}
