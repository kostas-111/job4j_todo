package ru.job4j.todo.exception;

public class LoginExistedException extends RuntimeException {
	public LoginExistedException(String login) {
		super("Пользователь с логином " + login + " уже существует");
	}
}
