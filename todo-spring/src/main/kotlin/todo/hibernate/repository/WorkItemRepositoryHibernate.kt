package todo.hibernate.repository

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.criteria.Path
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import todo.domain.model.WorkItem
import todo.domain.model.WorkItemsFilter
import todo.domain.repository.UserRepository
import todo.domain.repository.WorkItemRepository
import todo.domain.value.Difficulty
import todo.domain.value.State
import todo.domain.value.Status
import todo.domain.value.Value
import todo.hibernate.EntityMapper
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
            id = id ?: UUID.randomUUID()

            updatedDateTime = LocalDateTime.now()
            createdDateTime = createdDateTime ?: updatedDateTime

            number = getNextNumber()
        }

        entityManager.persist(entity)

        return mapper.entityModel(entity)
    }

    override fun find(filter: WorkItemsFilter): List<WorkItem> {
        val criteriaBuilder = entityManager.criteriaBuilder

        var query = criteriaBuilder.createQuery(WorkItemEntity::class.java)

        val Entity = query.from(WorkItemEntity::class.java)

        with(criteriaBuilder) {
            query = query.select(Entity)

            query = query.distinct(true)

            val restrictions: MutableList<Predicate> = mutableListOf()

            filter.text?.let { text ->
                val title: Path<String> = Entity["title"]
                restrictions.add(or(like(title, "%$text%")))
            }

            if (filter.state.isNotEmpty()) {
                val state: Path<State> = Entity["state"]
                restrictions.add(or(*filter.state.map { equal(state, it) }.toTypedArray()))
            }

            if (filter.status.isNotEmpty()) {
                val status: Path<Status> = Entity["status"]
                restrictions.add(or(*filter.status.map { equal(status, it) }.toTypedArray()))
            }

            if (filter.value.isNotEmpty()) {
                val value: Path<Value> = Entity["value"]
                restrictions.add(or(*filter.value.map { equal(value, it) }.toTypedArray()))
            }

            if (filter.difficulty.isNotEmpty()) {
                val difficulty: Path<Difficulty> = Entity["difficulty"]
                restrictions.add(or(*filter.difficulty.map { equal(difficulty, it) }.toTypedArray()))
            }

            query = query.where(and(*restrictions.toTypedArray()))

            val createdDateTime: Path<LocalDateTime> = Entity["createdDateTime"]

            query = query.orderBy(desc(createdDateTime))
        }

        val typedQuery = entityManager.createQuery(query)
        val typedResults = typedQuery.resultList
        val entityResults = typedResults.map(mapper::entityModel)

        return entityResults
    }

    private fun getNextNumber(): Int {

        val criteriaBuilder = entityManager.criteriaBuilder

        val query = criteriaBuilder.createQuery(Int::class.java)

        val root: Root<WorkItemEntity> = query.from(WorkItemEntity::class.java)

        query.select(criteriaBuilder.max(root.get("number")))

        val number = 1 + Optional.ofNullable(entityManager.createQuery(query).singleResult).orElse(0)

        return number
    }
}
