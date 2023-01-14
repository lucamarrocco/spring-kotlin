package todo.api

import todo.domain.model.Todo

class TodoApi: RestApi("http://localhost:8080") {

    suspend fun getTodos(): List<Todo> = get("todo")

    suspend fun addTodo(todo: Todo): Todo = post("todo", todo)
}
