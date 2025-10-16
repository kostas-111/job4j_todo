package ru.job4j.todo.repository.impl;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskRepository;
import ru.job4j.todo.utils.HibernateTransactionHelper;

import java.util.List;
import java.util.Optional;

@Repository
public class HbmTaskRepository implements TaskRepository {

    private final HibernateTransactionHelper transactionHelper;
    private final SessionFactory sessionFactory;

    public HbmTaskRepository(HibernateTransactionHelper transactionHelper,
		SessionFactory sessionFactory) {
		this.transactionHelper = transactionHelper;
		this.sessionFactory = sessionFactory;
	}

    @Override
    public Optional<Task> findById(Integer id) {
        return transactionHelper.executeWithTransaction(sessionFactory, session ->
                Optional.ofNullable(session.get(Task.class, id))
        );
    }

    @Override
    public Task save(Task task) {
        return transactionHelper.executeWithTransaction(sessionFactory, session -> {
            session.save(task);
            return task;
        });
    }

    @Override
    public boolean deleteById(Integer id) {
        return transactionHelper.executeWithTransaction(sessionFactory, session -> {
           int deletedCount = session.createQuery(
               "DELETE FROM Task WHERE id = :taskId")
               .setParameter("taskId", id)
               .executeUpdate();
            return deletedCount > 0;
        });
    }

    @Override
    public boolean update(Task task) {
        return transactionHelper.executeWithTransaction(sessionFactory, session -> {
            int updateCount = session.createQuery(
                "UPDATE Task SET title = :fTitle, description = :fDescription WHERE id = :fId")
                .setParameter("fTitle", task.getTitle())
                .setParameter("fDescription", task.getDescription())
                .setParameter("fId", task.getId())
                .executeUpdate();
            return updateCount > 0;
        });
    }

    @Override
    public boolean markAsCompleted(Integer id) {
        return transactionHelper.executeWithTransaction(sessionFactory, session -> {
            int updateCount = session.createQuery(
                    "UPDATE Task SET done = true WHERE id = :taskId")
                .setParameter("taskId", id)
                .executeUpdate();
            return updateCount > 0;
        });
    }

    @Override
    public List<Task> findAll() {
        return transactionHelper.executeWithTransaction(sessionFactory, session ->
                session.createQuery("FROM Task", Task.class).list()
        );
    }

    @Override
    public List<Task> findByDone(boolean done) {
        return transactionHelper.executeWithTransaction(sessionFactory, session ->
                session.createQuery("FROM Task WHERE done = :done ORDER BY created DESC", Task.class)
                        .setParameter("done", done)
                        .list()
        );
    }
}
