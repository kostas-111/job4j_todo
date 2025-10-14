package ru.job4j.todo.exception;

public class TaskNotFoundException extends RuntimeException {
	public TaskNotFoundException(Integer id) {
		super("Задание с ID " + id + " не найдено");
	}
}