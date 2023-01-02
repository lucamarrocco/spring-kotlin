package spring.kotlin.domain.repository

import spring.kotlin.domain.model.Todo

interface TodoRepository {

    fun addTodo(todo: Todo): Todo
}
