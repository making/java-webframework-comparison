package domain.service.todo;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.google.inject.persist.Transactional;

import domain.common.exception.BusinessException;
import domain.common.exception.ResourceNotFoundException;
import domain.model.Todo;

@Singleton
public class TodoService {

	private static final long MAX_UNFINISHED_COUNT = 5;
	@Inject
	protected Provider<EntityManager> entityManager;

	public EntityManager getEntityManager() {
		return entityManager.get();
	}

	public List<Todo> findAll() {
		TypedQuery<Todo> q = getEntityManager().createNamedQuery(
				"Todo.findAll", Todo.class);
		return q.getResultList();
	}

	public Todo findOne(Integer todoId) {
		Todo todo = getEntityManager().find(Todo.class, todoId);
		if (todo == null) {
			throw new ResourceNotFoundException(
					"[E404] The requested Todo is not found. (id=" + todoId
							+ ")");
		}
		return todo;
	}

	@Transactional
	public Todo create(Todo todo) {
		TypedQuery<Long> q = getEntityManager().createQuery(
				"SELECT COUNT(x) FROM Todo x WHERE x.finished = :finished",
				Long.class).setParameter("finished", false);
		long unfinishedCount = q.getSingleResult();
		if (unfinishedCount >= MAX_UNFINISHED_COUNT) {
			throw new BusinessException(
					"[E001] The count of un-finished Todo must not be over "
							+ MAX_UNFINISHED_COUNT + ".");
		}

		todo.setFinished(false);
		todo.setCreatedAt(new Date());
		getEntityManager().persist(todo);
		getEntityManager().flush();
		return todo;
	}

	@Transactional
	public Todo finish(Integer todoId) {
		Todo todo = findOne(todoId);
		if (todo.isFinished()) {
			throw new BusinessException(
					"[E002] The requested Todo is already finished. (id="
							+ todoId + ")");
		}
		todo.setFinished(true);
		getEntityManager().merge(todo);
		return todo;
	}

	@Transactional
	public void delete(Integer todoId) {
		Todo todo = findOne(todoId);
		getEntityManager().remove(todo);
	}
}
