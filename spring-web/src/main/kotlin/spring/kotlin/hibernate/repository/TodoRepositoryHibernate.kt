package spring.kotlin.hibernate.repository

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.TypedQuery
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Expression
import org.springframework.stereotype.Repository
import spring.kotlin.domain.model.Todo
import spring.kotlin.domain.model.TodoQuery
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

        return mapTodo(entity)
    }

    private fun mapTodo(entity: TodoEntity) = Todo(entity.id!!, entity.description!!)

    override fun findTodo(todo: TodoQuery): List<Todo> {

        val criteriaBuilder = entityManager.criteriaBuilder

        val criteriaQuery = criteriaBuilder.createQuery(TodoEntity::class.java)

        val root = criteriaQuery.from(TodoEntity::class.java)

        val likeDescription = todo.description.map { description ->
            criteriaBuilder.like(root["description"], "%$description%")
        }

        val orDescription = criteriaBuilder.or(*likeDescription.toTypedArray())

        criteriaQuery.select(root)
            .distinct(true)
            .where(orDescription)

        val query = entityManager.createQuery(criteriaQuery)

        return query.resultList.map(this::mapTodo)
    }
}
