package spring.kotlin.domain.repository

import spring.kotlin.domain.model.Todo
import spring.kotlin.domain.model.TodoQuery

interface TodoRepository {

    fun addTodo(todo: Todo): Todo

    fun findTodo(query: TodoQuery): List<Todo>
}
