package app.todo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import app.common.exception.BusinessExceptionHandlerFilter;
import app.common.exception.ResourceNotFoundExceptionHandlerFilter;
import domain.model.Todo;
import domain.service.todo.TodoService;
import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.params.PathParam;

@Singleton
public class TodoRestController {
	@Inject
	protected TodoService todoService;

	public Result getTodos() {
		List<Todo> todos = todoService.findAll();
		return Results.json().render(todos);
	}

	@FilterWith({ BusinessExceptionHandlerFilter.class })
	public Result postTodos(Context context, Todo todo) {
		Todo created = todoService.create(todo);
		return Results.status(201).json().render(created);
	}

	@FilterWith({ ResourceNotFoundExceptionHandlerFilter.class })
	public Result getTodo(Context context, @PathParam("todoId") Integer todoId) {
		Todo todo = todoService.findOne(todoId);
		return Results.json().render(todo);
	}

	@FilterWith({ BusinessExceptionHandlerFilter.class,
			ResourceNotFoundExceptionHandlerFilter.class })
	public Result putTodo(Context context, @PathParam("todoId") Integer todoId) {
		Todo finished = todoService.finish(todoId);
		return Results.json().render(finished);
	}

	@FilterWith({ BusinessExceptionHandlerFilter.class,
			ResourceNotFoundExceptionHandlerFilter.class })
	public Result deleteTodo(Context context,
			@PathParam("todoId") Integer todoId) {
		todoService.delete(todoId);
		return Results.noContent();
	}
}
