package todo.web.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import todo.domain.model.Todo
import todo.domain.model.TodoQuery
import todo.domain.repository.TodoRepository


@RestController
@CrossOrigin(origins = ["http://localhost:3000"])
class TodoController {

    @Autowired
    private lateinit var todoRepository: TodoRepository

    @Transactional
    @PostMapping("/todo", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createTodo(@RequestBody todo: Todo) = todoRepository.addTodo(todo)

    @Transactional(readOnly = true)
    @GetMapping("/todo")
    fun findTodo(query: TodoQuery) = todoRepository.findTodo(query)
}
