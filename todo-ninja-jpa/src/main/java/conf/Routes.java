/**
 * Copyright (C) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package conf;

import ninja.AssetsController;
import ninja.Router;
import ninja.application.ApplicationRoutes;
import ninja.utils.NinjaProperties;
import app.todo.TodoController;
import app.todo.TodoRestController;

import com.google.inject.Inject;

public class Routes implements ApplicationRoutes {

	private NinjaProperties ninjaProperties;

	@Inject
	public Routes(NinjaProperties ninjaProperties) {
		this.ninjaProperties = ninjaProperties;

	}

	/**
	 * Using a (almost) nice DSL we can configure the router.
	 * 
	 * The second argument NinjaModuleDemoRouter contains all routes of a
	 * submodule. By simply injecting it we activate the routes.
	 * 
	 * @param router
	 *            The default router of this application
	 */
	@Override
	public void init(Router router) {

		// Todo routes
		router.GET().route("/todo/list").with(TodoController.class, "list");
		router.POST().route("/todo/create")
				.with(TodoController.class, "create");
		router.POST().route("/todo/finish")
				.with(TodoController.class, "finish");
		router.POST().route("/todo/delete")
				.with(TodoController.class, "delete");

		// REST API for Todos
		router.GET().route("/api/todos")
				.with(TodoRestController.class, "getTodos");
		router.POST().route("/api/todos")
				.with(TodoRestController.class, "postTodos");

		// REST API for Todo
		router.GET().route("/api/todos/{todoId}")
				.with(TodoRestController.class, "getTodo");
		router.PUT().route("/api/todos/{todoId}")
				.with(TodoRestController.class, "putTodo");
		router.DELETE().route("/api/todos/{todoId}")
				.with(TodoRestController.class, "deleteTodo");

		// etc
		router.GET().route("/").with(TodoController.class, "list");
		router.GET().route("/assets/.*").with(AssetsController.class, "serve");
	}

}
