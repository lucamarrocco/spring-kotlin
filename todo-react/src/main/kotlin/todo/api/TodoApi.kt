package todo.api

import base.Rest
import todo.model.Todo

object Api {

    private val httpClient = Rest("http://localhost:8080")

    suspend fun addTodo(todo: Todo): Todo =
        httpClient.POST("todo", todo)

    suspend fun findTodos(): List<Todo> =
        httpClient.GET("todo")

}
