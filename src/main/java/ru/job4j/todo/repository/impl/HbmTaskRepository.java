package ru.job4j.todo.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskRepository;
import java.util.List;
import java.util.Optional;

@Repository
public class HbmTaskRepository implements TaskRepository {

    private final SessionFactory sessionFactory;

    public HbmTaskRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Task> findById(Integer id) {
        return executeWithTransaction(session ->
                Optional.ofNullable(session.get(Task.class, id))
        );
    }

    @Override
    public Task save(Task task) {
        return executeWithTransaction(session -> {
            session.save(task);
            return task;
        });
    }

    @Override
    public boolean deleteById(Integer id) {
        return executeWithTransaction(session -> {
            Task task = session.get(Task.class, id);
            if (task != null) {
                session.delete(task);
                return true;
            }
            return false;
        });
    }

    @Override
    public boolean update(Task task) {
        return executeWithTransaction(session -> {
            session.update(task);
            return true;
        });
    }

    @Override
    public List<Task> findAll() {
        return executeWithTransaction(session ->
                session.createQuery("FROM Task", Task.class).list()
        );
    }

    @Override
    public List<Task> findByDone(boolean done) {
        return executeWithTransaction(session ->
                session.createQuery("FROM Task WHERE done = :done ORDER BY created DESC", Task.class)
                        .setParameter("done", done)
                        .list()
        );
    }

    private <T> T executeWithTransaction(java.util.function.Function<Session, T> function) {
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
