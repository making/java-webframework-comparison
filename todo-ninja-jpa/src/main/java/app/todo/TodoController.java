package app.todo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import domain.common.exception.BusinessException;
import domain.model.Todo;
import domain.service.todo.TodoService;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;

@Singleton
public class TodoController {
	@Inject
	protected TodoService todoService;

	public Result list() {
		List<Todo> todos = todoService.findAll();
		return Results.html().render("todos", todos);
	}

	public Result create(Context context, @JSR303Validation TodoForm form,
			Validation validation) {
		if (validation.hasViolations()) {
			return list().render("errorMessage", "Validation error!").template(
					"/app/todo/TodoController/list.ftl.html");
		}
		Todo todo = new Todo();
		todo.setTodoTitle(form.getTodoTitle());
		try {
			todoService.create(todo);
		} catch (BusinessException e) {
			return list().render("errorMessage", e.getMessage()).template(
					"/app/todo/TodoController/list.ftl.html");
		}
		context.getFlashCookie().put("successMessage", "Created successfully!");
		return Results.redirect(context.getContextPath() + "/todo/list");
	}

	public Result finish(Context context, @Param("todoId") Integer todoId) {
		try {
			todoService.finish(todoId);
		} catch (BusinessException e) {
			return list().render("errorMessage", e.getMessage()).template(
					"/app/todo/TodoController/list.ftl.html");
		}
		return Results.redirect(context.getContextPath() + "/todo/list");
	}

	public Result delete(Context context, @Param("todoId") Integer todoId) {
		todoService.delete(todoId);
		return Results.redirect(context.getContextPath() + "/todo/list");
	}
}
