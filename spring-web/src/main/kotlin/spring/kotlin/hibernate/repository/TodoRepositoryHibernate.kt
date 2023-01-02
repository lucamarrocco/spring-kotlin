package spring.kotlin.hibernate.repository

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import spring.kotlin.domain.model.Todo
import spring.kotlin.domain.model.TodoQuery
import spring.kotlin.domain.repository.TodoRepository
import spring.kotlin.hibernate.entity.EntityMapper
import spring.kotlin.hibernate.entity.TodoEntity
import java.util.*

@Repository
class TodoRepositoryHibernate : TodoRepository {

    @Autowired
    private lateinit var mapper: EntityMapper

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun addTodo(todo: Todo): Todo {
        val entity = mapper.modelEntity(todo)

        entity.id = UUID.randomUUID()

        entityManager.persist(entity)

        return mapper.entityModel(entity)
    }

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

        return query.resultList.map(mapper::entityModel)
    }
}
