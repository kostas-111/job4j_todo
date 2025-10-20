package ru.job4j.todo.repository.impl;

import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.UserRepository;
import ru.job4j.todo.utils.HibernateTransactionHelper;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
public class HbmUserRepository implements UserRepository {

	private final HibernateTransactionHelper transactionHelper;

	public HbmUserRepository(HibernateTransactionHelper transactionHelper) {
		this.transactionHelper = transactionHelper;
	}

	@Override
	public void save(User user) {
		transactionHelper.run(session ->
			session.save(user));
	}

	@Override
	public Optional<User> findById(Integer id) {
		return transactionHelper.optional(
			"FROM User WHERE id = :fId", User.class,
				  Map.of("fId", id)
		);
	}

	@Override
	public Optional<User> findByLoginAndPassword(String login, String password) {
		return transactionHelper.optional(
			"FROM User WHERE login = :fLogin and password = :fPassword", User.class,
				  Map.of("fLogin", login,
				   		 "fPassword", password)
		);
	}

	@Override
	public Optional<User> findByLogin(String login) {
		return transactionHelper.optional(
			"FROM User WHERE login = :fLogin", User.class,
				  Map.of("fLogin", login)
		);
	}

	@Override
	public boolean deleteById(int id) {
		return transactionHelper.run(
			"DELETE FROM User WHERE id = :fId",
				  Map.of("fId", id)
		);
	}

	@Override
	public Collection<User> findAll() {
		return transactionHelper.query("FROM User ORDER BY created DESC", User.class);
	}
}
