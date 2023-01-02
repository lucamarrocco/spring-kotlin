## Configure MapStruct to convert between Model and Entity

### Configure Kotlin Annotation Processor Plugin

```kotlin
plugins {
    kotlin("kapt") version "1.7.22"
}
```

### Add MapStruct Dependency

```kotlin
dependencies {
    implementation("org.mapstruct:mapstruct:1.5.3.Final")
    kapt("org.mapstruct:mapstruct-processor:1.5.3.Final")
}
```

### Add EntityMapper to map Todo Model Entity

in ```hibernate.entity``` create a file named ```EntityMapper.kt```

```kotlin
package spring.kotlin.hibernate.entity

import org.mapstruct.Mapper
import spring.kotlin.domain.model.Todo

@Mapper(componentModel = "spring")
abstract class EntityMapper {

    abstract fun entityModel(todo: TodoEntity): Todo

    abstract fun modelEntity(todo: Todo): TodoEntity
}
```

### Inject EntityMapper in TodoRepository

in ```TodoRepositoryHibernate``` add ```EntityMapper``` dependency

```kotlin
class TodoRepositoryHibernate : TodoRepository {

    @Autowired
    private lateinit var mapper: EntityMapper
}
```

and use that mapper to return Todo model from an hibernate entity

```kotlin
    override fun addTodo(todo: Todo): Todo {
        val entity = mapper.modelEntity(todo)
    
        return mapper.entityModel(entity)
    }

    override fun findTodo(todo: TodoQuery): List<Todo> {
    
        return query.resultList.map(mapper::entityModel)
    }
```
