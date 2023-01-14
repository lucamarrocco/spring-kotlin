package todo.hibernate.repository

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.criteria.Path
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import todo.domain.model.Todo
import todo.domain.model.TodoQuery
import todo.domain.repository.TodoRepository
import todo.hibernate.entity.EntityMapper
import todo.hibernate.entity.TodoEntity
import java.time.LocalDateTime
import java.util.*

@Repository
class TodoRepositoryHibernate : TodoRepository {

    @Autowired
    private lateinit var mapper: EntityMapper

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun addTodo(todo: Todo): Todo {
        val entity = mapper.modelEntity(todo)

        with(entity) {
            id = UUID.randomUUID()
            created = LocalDateTime.now()
        }

        entityManager.persist(entity)

        return mapper.entityModel(entity)
    }

    override fun findTodo(query: TodoQuery): List<Todo> {

        val criteriaBuilder = entityManager.criteriaBuilder

        var criteriaQuery = criteriaBuilder.createQuery(TodoEntity::class.java)

        val root = criteriaQuery.from(TodoEntity::class.java)
        val createdPath: Path<LocalDateTime> = root["created"]
        val descriptionPath: Path<String> = root["description"]

        criteriaQuery = criteriaQuery.select(root)
        criteriaQuery = criteriaQuery.distinct(true)


        if (query.description.isNotEmpty()) {
            val likeDescription = query.description.map { description ->
                criteriaBuilder.like(
                    descriptionPath,
                    "%$description%"
                )
            }

            val orDescription = criteriaBuilder.or(*likeDescription.toTypedArray())

            criteriaQuery = criteriaQuery
                .where(orDescription)
        }

        val createdAsc = criteriaBuilder.desc(createdPath)

        criteriaQuery = criteriaQuery
            .orderBy(createdAsc)

        val todoQuery = entityManager.createQuery(criteriaQuery)

        return todoQuery.resultList.map(mapper::entityModel)
    }
}
