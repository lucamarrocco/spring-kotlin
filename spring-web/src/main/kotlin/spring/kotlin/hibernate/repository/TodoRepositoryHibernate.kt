package spring.kotlin.hibernate.repository

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository
import spring.kotlin.domain.model.Todo
import spring.kotlin.domain.repository.TodoRepository
import spring.kotlin.hibernate.entity.TodoEntity
import java.util.*

@Repository
class TodoRepositoryHibernate : TodoRepository {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun addTodo(todo: Todo): Todo {
        val entity = TodoEntity()

        entity.id = UUID.randomUUID()

        entity.description = todo.description

        entityManager.persist(entity)

        return Todo(entity.id!!, entity.description!!)
    }
}
