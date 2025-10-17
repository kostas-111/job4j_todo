package ru.job4j.todo.exception;

public class LoginPasswordWrongException extends RuntimeException {
	public LoginPasswordWrongException() {
		super("Логин или пароль введены неверно");
	}
}
