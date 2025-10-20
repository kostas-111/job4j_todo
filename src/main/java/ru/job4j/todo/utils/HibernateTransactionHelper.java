package ru.job4j.todo.utils;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;


/**
 * Универсальный класс для выполнения CRUD операций с использованием Hibernate.
 * Предоставляет удобные методы для работы с базой данных с автоматическим управлением транзакциями.
 *
 */
@Component
@AllArgsConstructor
public class HibernateTransactionHelper {

	private final SessionFactory sf;

	/**
	 * Выполняет операцию без возвращаемого значения в транзакции.
	 *
	 * @param command потребитель, содержащий операцию для выполнения с сессией Hibernate
	 */
	public void run(Consumer<Session> command) {
		tx(session -> {
				command.accept(session);
				return null;
			}
		);
	}

	/**
	 * Выполняет HQL запрос на обновление или удаление данных с параметрами.
	 *
	 * @param query HQL запрос для выполнения (UPDATE, DELETE и т.д.)
	 * @param args карта параметров для запроса, где ключ - имя параметра, значение - значение параметра
	 */
	public boolean run(String query, Map<String, Object> args) {
		AtomicInteger updatedRows = new AtomicInteger();
		Consumer<Session> command = session -> {
			var sq = session
				.createQuery(query);
			for (Map.Entry<String, Object> arg : args.entrySet()) {
				sq.setParameter(arg.getKey(), arg.getValue());
			}
			updatedRows.set(sq.executeUpdate());
		};
		run(command);
		return updatedRows.get() > 0;
	}

	/**
	 * Выполняет HQL запрос и возвращает один результат в виде Optional.
	 * Используется для запросов, которые должны вернуть не более одного результата.
	 *
	 * @param <T> тип возвращаемого результата
	 * @param query HQL запрос для выполнения
	 * @param cl класс возвращаемого типа
	 * @param args карта параметров для запроса
	 * @return Optional содержащий результат или пустой Optional, если результат не найден
	 */
	public <T> Optional<T> optional(String query, Class<T> cl, Map<String, Object> args) {
		Function<Session, Optional<T>> command = session -> {
			var sq = session
				.createQuery(query, cl);
			for (Map.Entry<String, Object> arg : args.entrySet()) {
				sq.setParameter(arg.getKey(), arg.getValue());
			}
			return sq.uniqueResultOptional();
		};
		return tx(command);
	}

	/**
	 * Выполняет HQL запрос без параметров и возвращает список результатов.
	 *
	 * @param <T> тип элементов в возвращаемом списке
	 * @param query HQL запрос для выполнения
	 * @param cl класс элементов возвращаемого списка
	 * @return список результатов запроса
	 */
	public <T> List<T> query(String query, Class<T> cl) {
		Function<Session, List<T>> command = session -> session
			.createQuery(query, cl)
			.list();
		return tx(command);
	}

	/**
	 * Выполняет параметризованный HQL запрос и возвращает список результатов.
	 *
	 * @param <T> тип элементов в возвращаемом списке
	 * @param query HQL запрос для выполнения
	 * @param cl класс элементов возвращаемого списка
	 * @param args карта параметров для запроса
	 * @return список результатов запроса
	 */
	public <T> List<T> query(String query, Class<T> cl, Map<String, Object> args) {
		Function<Session, List<T>> command = session -> {
			var sq = session
				.createQuery(query, cl);
			for (Map.Entry<String, Object> arg : args.entrySet()) {
				sq.setParameter(arg.getKey(), arg.getValue());
			}
			return sq.list();
		};
		return tx(command);
	}

	/**
	 * Выполняет операцию в транзакции с автоматическим управлением жизненным циклом транзакции.
	 * Открывает сессию, начинает транзакцию, выполняет команду и коммитит изменения.
	 * В случае ошибки выполняет откат транзакции.
	 *
	 * @param <T> тип возвращаемого результата
	 * @param command функция, содержащая операцию для выполнения с сессией Hibernate
	 * @return результат выполнения команды
	 */
	public <T> T tx(Function<Session, T> command) {
		Session session = sf.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			T rsl = command.apply(session);
			transaction.commit();
			return rsl;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw e;
		} finally {
			session.close();
		}
	}
}