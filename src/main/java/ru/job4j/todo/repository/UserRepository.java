package ru.job4j.todo.repository;

import ru.job4j.todo.model.User;
import java.util.Collection;
import java.util.Optional;

public interface UserRepository {

	User save(User user);

	Optional<User> findById(Integer id);

	Optional<User> findByLoginAndPassword(String login, String password);

	Optional<User> findByLogin(String login);

	boolean deleteById(int id);

	Collection<User> findAll();
}
