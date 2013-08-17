package app.todo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TodoForm {
	@NotNull
	@Size(min = 1, max = 128)
	private String todoTitle;

	public String getTodoTitle() {
		return todoTitle;
	}

	public void setTodoTitle(String todoTitle) {
		this.todoTitle = todoTitle;
	}
}
