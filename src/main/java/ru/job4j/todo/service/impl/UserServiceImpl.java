package ru.job4j.todo.service.impl;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;

import ru.job4j.todo.exception.LoginExistedException;
import ru.job4j.todo.exception.LoginPasswordWrongException;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.UserRepository;
import ru.job4j.todo.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User save(User user) {
		try {
			return userRepository.save(user);
		} catch (ConstraintViolationException e) {
			throw new LoginExistedException(user.getLogin());
		}
	}

	@Override
	public Optional<User> findByLoginAndPassword(String login, String password) {
		Optional<User> existingUser = userRepository.findByLoginAndPassword(login, password);
		if (existingUser.isEmpty()) {
			throw new LoginPasswordWrongException();
		}
		return existingUser;
	}
}
