package todo.hibernate.repository

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.criteria.Path
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import todo.domain.model.WorkItem
import todo.domain.model.WorkItemQuery
import todo.domain.repository.UserRepository
import todo.domain.repository.WorkItemRepository
import todo.hibernate.entity.EntityMapper
import todo.hibernate.entity.WorkItemEntity
import java.time.LocalDateTime
import java.util.*

@Repository
class WorkItemRepositoryHibernate : WorkItemRepository {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    @Autowired
    private lateinit var mapper: EntityMapper

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun save(workItem: WorkItem): WorkItem {
        val entity = mapper.modelEntity(workItem, userRepository)

        with(entity) {
            id = UUID.randomUUID()
            created = LocalDateTime.now()
        }

        entityManager.persist(entity)

        return mapper.entityModel(entity)
    }

    override fun find(workItem: WorkItemQuery): List<WorkItem> {
        val criteriaBuilder = entityManager.criteriaBuilder

        var query = criteriaBuilder.createQuery(WorkItemEntity::class.java)

        val Entity = query.from(WorkItemEntity::class.java)

        with(criteriaBuilder) {
            query = query.select(Entity)

            query = query.distinct(true)

            workItem.text?.let { text ->
                val title: Path<String> = Entity["title"]
                query = query.where(or(like(title, "%$text%")))
            }

            val created: Path<LocalDateTime> = Entity["created"]
            query = query.orderBy(desc(created))
        }

        val typedQuery = entityManager.createQuery(query)
        val typedResults = typedQuery.resultList
        val entityResults = typedResults.map(mapper::entityModel)
        return entityResults
    }
}
