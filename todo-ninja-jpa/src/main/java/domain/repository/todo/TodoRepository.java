package domain.repository.todo;

import java.util.List;

import domain.model.Todo;

public interface TodoRepository {

	List<Todo> findAllOrderById();

	long countByFinished(boolean finished);

	Todo findOne(Integer todoId);

	Todo save(Todo todo);

	void remove(Integer todoId);

}