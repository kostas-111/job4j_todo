package ru.job4j.todo.repository.impl;

import org.springframework.stereotype.Repository;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskRepository;
import ru.job4j.todo.utils.HibernateTransactionHelper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class HbmTaskRepository implements TaskRepository {

    private final HibernateTransactionHelper transactionHelper;

    public HbmTaskRepository(HibernateTransactionHelper transactionHelper) {
		this.transactionHelper = transactionHelper;
	}

    @Override
    public Optional<Task> findById(Integer id) {
        return transactionHelper.optional(
			    "FROM Task WHERE id = :fId", Task.class,
			           Map.of("fId", id)
        );
    }

	@Override
	public void save(Task task) {
		transactionHelper.run(session ->
			session.save(task));
	}

	@Override
	public boolean deleteById(Integer id) {
		return transactionHelper.run(
			"DELETE FROM Task WHERE id = :fId",
					Map.of("fId", id)
		);
	}

	@Override
	public boolean update(Task task) {
		return transactionHelper.run(
			"UPDATE Task SET title = :fTitle, "
					+ "description = :fDescription, "
					+ "priority_id = :fPriorityId "
					+ "WHERE id = :fId",
					Map.of("fTitle", task.getTitle(),
						   "fDescription", task.getDescription(),
							 "fPriorityId", task.getPriority().getId(),
						   "fId", task.getId()
					)
		);
	}

	@Override
	public boolean markAsCompleted(Integer id) {
		return transactionHelper.run(
					"UPDATE Task SET done = true WHERE id = :fId",
						   Map.of("fId", id)
		);
	}

	@Override
	public List<Task> findAll() {
		return transactionHelper.query("FROM Task f JOIN FETCH f.priority ORDER BY created DESC",
				Task.class);
	}

	@Override
	public List<Task> findByDone(boolean done) {
		return transactionHelper.query(
			"FROM Task f JOIN FETCH f.priority WHERE done = :done ORDER BY created DESC", Task.class,
				  Map.of("done", done)
		);
	}
}
