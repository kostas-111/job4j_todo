package ru.job4j.todo.repository.impl;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.UserRepository;
import ru.job4j.todo.utils.HibernateTransactionHelper;

import java.util.Collection;
import java.util.Optional;

@Repository
public class HbmUserRepository implements UserRepository {

	private final HibernateTransactionHelper transactionHelper;
	private final SessionFactory sessionFactory;

	public HbmUserRepository(HibernateTransactionHelper transactionHelper,
		SessionFactory sessionFactory) {
		this.transactionHelper = transactionHelper;
		this.sessionFactory = sessionFactory;
	}

	@Override
	public User save(User user) {
		return transactionHelper.executeWithTransaction(sessionFactory, session -> {
			session.save(user);
			return user;
		});
	}

	@Override
	public Optional<User> findById(Integer id) {
		return transactionHelper.executeWithTransaction(sessionFactory, session ->
			Optional.ofNullable(session.get(User.class, id))
		);
	}

	@Override
	public Optional<User> findByLoginAndPassword(String login, String password) {
		return transactionHelper.executeWithTransaction(sessionFactory, session ->
			session.createQuery("FROM User WHERE login = :login and password = :password", User.class)
				.setParameter("login", login)
				.setParameter("password", password)
				.uniqueResultOptional()
		);
	}

	@Override
	public boolean deleteById(int id) {
		return transactionHelper.executeWithTransaction(sessionFactory, session -> {
			int deletedCount = session.createQuery(
					"DELETE FROM User WHERE id = :userId")
				.setParameter("userId", id)
				.executeUpdate();
			return deletedCount > 0;
		});
	}

	@Override
	public Collection<User> findAll() {
		return transactionHelper.executeWithTransaction(sessionFactory, session ->
			session.createQuery("FROM User", User.class).list()
		);
	}

}
