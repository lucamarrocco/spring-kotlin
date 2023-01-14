## Setting up simple Hibernate Entity

### Add Spring Data JPA dependency

in build.gradle.kts

add a dependency to string data jpa which is the library that include necessary functionality to configure things to
save and change entity using an existing persistence layer.

```kotlin
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}
```

add postgresql driver

```kotlin
dependencies {
    runtimeOnly("org.postgresql:postgresql")
}
```

### Configure Hibernate Database Connection (PostgreSQL)

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: create
    database-platform: org.hibernate.dialect.PostgreSQLDialect
  datasource:
    url: "jdbc:postgresql://localhost:5432/postgres"
```

## Entity and Repository

### Domain Model

in ```domain.model``` package, create a file named ```Todo.kt```

```kotlin
package todo.domain.model

import java.util.*

data class Todo(

    val id: UUID?,

    val description: String
)
```

### Entity

in ```hibernate.entity``` package, create a file named ```TodoEntity.kt```

```kotlin
package todo.hibernate.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.lang.NonNull
import java.util.*

@Table(name = "TODO")
@Entity(name = "Todo")
class TodoEntity {

    @Id
    @Column(name = "TODO_ID", nullable = false, updatable = false)
    var id: UUID? = null

    @NonNull
    @Column(name = "DESCRIPTION", nullable = false, updatable = false)
    var description: String? = null
}
```

### Repository

in ```domain.repository``` package, create a file named ```TodoRepository.kt```

```kotlin
package todo.domain.repository

import todo.domain.model.Todo

interface TodoRepository {

    fun addTodo(todo: Todo): Todo
}
```

in ```hibernate.repository``` package, create a file named ```TodoRepositoryHibernate.kt```

```kotlin
package todo.hibernate.repository

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository
import todo.domain.model.Todo
import todo.domain.repository.TodoRepository
import todo.hibernate.entity.TodoEntity
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
```

### Controller

in ```web.controller``` package, create a file named ```TodoController.kt```

```kotlin
package todo.web.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import todo.domain.model.Todo
import todo.domain.repository.TodoRepository


@RestController
class TodoController {

    @Autowired
    private lateinit var todoRepository: TodoRepository

    @Transactional
    @PostMapping("/todo", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createTodo(@RequestBody todo: Todo) = todoRepository.addTodo(todo)
}
```
