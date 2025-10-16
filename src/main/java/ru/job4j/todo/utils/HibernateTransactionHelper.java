package ru.job4j.todo.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

@Component
public class HibernateTransactionHelper {

	public <T> T executeWithTransaction(SessionFactory sessionFactory,
										java.util.function.Function<Session, T> function) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		try {
			T result = function.apply(session);
			session.getTransaction().commit();
			return result;
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw e;
		}
	}
}