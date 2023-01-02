## Setting up simple Hibernate Query

### Add a Query model to find entities

in package ```domain.model``` create a file named ```TodoQuery.kt```

```kotlin
package spring.kotlin.domain.model

class TodoQuery {
    var description: List<String> = listOf()
}
```

### Add find method in Todo repository

in ```TodoRepository.kt``` add a method to find entities using a query object

```kotlin
interface TodoRepository {
    fun findTodo(query: TodoQuery): List<Todo>
}
```

### Add find method in Todo repository (Hibernate)

in ```TodoRepositoryHibernate.kt``` add a method to find entities using a query object

```kotlin
class TodoRepositoryHibernate : TodoRepostiory {

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
```

### Add find method in Todo controller

in ```TodoController.kt``` add a method to find entities using a query object

```kotlin
class TodoController {

    @Transactional(readOnly = true)
    @GetMapping("/todo")
    fun findTodo(query: TodoQuery) = todoRepository.findTodo(query)
}
```
