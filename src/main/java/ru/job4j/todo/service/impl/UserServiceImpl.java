package ru.job4j.todo.service.impl;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.UserRepository;
import ru.job4j.todo.service.UserService;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public Optional<User> findByLoginAndPassword(String login, String password) {
		return userRepository.findByLoginAndPassword(login, password);
	}
}
