package domain.repository.todo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.google.inject.persist.Transactional;

import domain.model.Todo;

@Singleton
public class TodoRepositoryImpl implements TodoRepository {

	@Inject
	protected Provider<EntityManager> entityManager;

	protected EntityManager getEntityManager() {
		return entityManager.get();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see domain.repository.todo.TodoRepository#findAll()
	 */
	@Override
	public List<Todo> findAllOrderById() {
		TypedQuery<Todo> q = getEntityManager().createNamedQuery(
				"Todo.findAll", Todo.class);
		return q.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see domain.repository.todo.TodoRepository#countByFinished
	 */
	@Override
	public long countByFinished(boolean finished) {
		TypedQuery<Long> q = getEntityManager().createQuery(
				"SELECT COUNT(x) FROM Todo x WHERE x.finished = :finished",
				Long.class).setParameter("finished", finished);
		long count = q.getSingleResult();
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see domain.repository.todo.TodoRepository#findOne(java.lang.Integer)
	 */
	@Override
	public Todo findOne(Integer todoId) {
		Todo todo = getEntityManager().find(Todo.class, todoId);
		return todo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see domain.repository.todo.TodoRepository#save(domain.model.Todo)
	 */
	@Override
	@Transactional
	public Todo save(Todo todo) {
		if (todo.getTodoId() != null) {
			getEntityManager().merge(todo);
		} else {
			getEntityManager().persist(todo);
		}
		getEntityManager().flush();
		return todo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see domain.repository.todo.TodoRepository#delete(java.lang.Integer)
	 */
	@Override
	@Transactional
	public void remove(Integer todoId) {
		Todo todo = findOne(todoId);
		getEntityManager().remove(todo);
	}
}
