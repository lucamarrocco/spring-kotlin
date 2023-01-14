package todo.domain.repository

import todo.domain.model.Todo
import todo.domain.model.TodoQuery

interface TodoRepository {

    fun addTodo(todo: Todo): Todo

    fun findTodo(query: TodoQuery): List<Todo>
}
