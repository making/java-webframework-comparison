package domain.service.todo;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.inject.persist.Transactional;

import domain.common.exception.BusinessException;
import domain.common.exception.ResourceNotFoundException;
import domain.model.Todo;
import domain.repository.todo.TodoRepository;

@Singleton
public class TodoService {

	private static final long MAX_UNFINISHED_COUNT = 5;
	@Inject
	protected TodoRepository todoRepository;

	public List<Todo> findAll() {
		List<Todo> todos = todoRepository.findAllOrderById();
		return todos;
	}

	public Todo findOne(Integer todoId) {
		Todo todo = todoRepository.findOne(todoId);
		if (todo == null) {
			throw new ResourceNotFoundException(
					"[E404] The requested Todo is not found. (id=" + todoId
							+ ")");
		}
		return todo;
	}

	@Transactional
	public Todo create(Todo todo) {
		long unfinishedCount = todoRepository.countByFinished(false);
		if (unfinishedCount >= MAX_UNFINISHED_COUNT) {
			throw new BusinessException(
					"[E001] The count of un-finished Todo must not be over "
							+ MAX_UNFINISHED_COUNT + ".");
		}

		todo.setFinished(false);
		todo.setCreatedAt(new Date());
		todoRepository.save(todo);
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
		todoRepository.save(todo);
		return todo;
	}

	@Transactional
	public void delete(Integer todoId) {
		todoRepository.remove(todoId);
	}
}
