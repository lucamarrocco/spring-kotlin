## Setting up simple KotlinJs for React

### Create a Kotlin / Js Project

```kotlin
plugins {
    kotlin("js") version "1.8.0"
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
package todo.hibernate.entity

import org.mapstruct.Mapper
import todo.domain.model.Todo

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

### Enabling Cross Origin Requests for a RESTful Web Service

```kotlin
@CrossOrigin(origins = ["http://localhost:8081"])
class TodoController
```

## Client

### Todo model

into package ```todo.model``` create a file named ```Todo.kt```

```kotlin
package todo.model

import kotlinx.serialization.Serializable

@Serializable
data class Todo(

    val id: String? = null,

    val description: String? = null,

    val created: String? = null
)
```

add serializable plugin and dependency in ```build.gradle.kts```

```kotlin
plugins {
    kotlin("plugin.serialization") version "1.8.0"
}
```

```kotlin
dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
}
```

### Create a class to execute REST requests

```kotlin
package todo.api

import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.w3c.fetch.CORS
import org.w3c.fetch.RequestInit
import org.w3c.fetch.RequestMode
import kotlin.js.json

open class RestApi(val baseUrl: String) {

    suspend inline fun <reified EntityType> post(path: String, body: EntityType? = null): EntityType =
        request("POST", path, body)

    suspend inline fun <reified EntityType> get(path: String, body: EntityType? = null): List<EntityType> =
        request("GET", path, body)

    suspend inline fun <reified T, reified R> request(method: String, path: String, entity: T?): R {
        val response = window
            .fetch(
                "$baseUrl/$path",
                RequestInit(
                    mode = RequestMode.CORS,
                    method = method,
                    headers = json().apply { this["content-type"] = "application/json" },
                    body = entity?.let { Json.encodeToString(entity) },
                )
            )
            .await()
            .text()
            .await()
        return Json.decodeFromString(response)
    }
}
```

### Create a class to execute REST requests to create and list todos

```kotlin
package todo.api

import todo.model.Todo

class TodoApi: RestApi("http://localhost:8080") {

    suspend fun getTodos(): List<Todo> = get("todo")

    suspend fun addTodo(todo: Todo): Todo = post("todo", todo)
}

```

### Create a React control to represent a list of todos

```kotlin
package todo.control

import react.FC
import react.Props
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ul
import todo.model.Todo

external interface TodoListProps : Props {
    var items: List<Todo>
}

val TodoList = FC<TodoListProps> { props ->
    ul {
        props.items
            .map { todo ->
                todo.description?.let {
                    li {
                        +it
                    }
                }
            }
    }
}
```

### Create a React component to enter a Todo form

```kotlin
package todo.control

import org.w3c.dom.HTMLInputElement
import react.FC
import react.Props
import react.dom.events.ChangeEventHandler
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.form
import react.dom.html.ReactHTML.input
import todo.model.Todo

typealias ItemEventHandler<T> = (item: T) -> Unit
typealias DismissEventHandler<T> = ItemEventHandler<T>
typealias ConfirmEventHandler<T> = ItemEventHandler<T>


external interface TodoFormProps : Props {
    var item: Todo

    var onDismiss: DismissEventHandler<Todo>
    var onConfirm: ConfirmEventHandler<Todo>

    var onChangeDescription: ChangeEventHandler<HTMLInputElement>
}

val TodoForm = FC<TodoFormProps> { props ->
    form {
        input {
            autoFocus = true
            name = "description"
            value = props.item.description ?: ""
            onChange = props.onChangeDescription
        }

        button {
            +"Cancel"
            onClick = { props.onDismiss(props.item) }
        }

        button {
            +"Save"
            onClick = { props.onConfirm(props.item) }
        }

        onSubmit = { event -> event.preventDefault(); props.onConfirm(props.item) }
    }
}
```


### Create a React component to display a Todo page

```kotlin
package todo.control

import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLFormElement
import org.w3c.dom.HTMLInputElement
import react.FC
import react.Fragment
import react.Props
import react.dom.events.ChangeEventHandler
import react.dom.events.FormEventHandler
import react.dom.events.MouseEventHandler
import react.dom.html.ReactHTML.h1
import react.useEffectOnce
import react.useState
import todo.api.TodoApi
import todo.model.Todo


val api = TodoApi()

val mainScope = MainScope()

external interface TodoPageProps : Props

val TodoPage = FC<TodoPageProps> {

    val (todo, setTodo) = useState(Todo())
    val (todos, setTodos) = useState(listOf<Todo>())

    fun dismissForm() {
        setTodo(Todo())
    }

    fun completeForm() {
        if (null == todo.description) return

        mainScope.launch {
            api.addTodo(todo)
            setTodo(Todo())
            setTodos(api.getTodos())
            window.alert("Todo saved!!")
        }
    }

    fun fetchTodos() {
        mainScope.launch {
            setTodos(api.getTodos())
        }
    }


    val handleDismissForm: ItemEventHandler<Todo> = { todo ->
        dismissForm()
    }

    val handleConfirmForm: ItemEventHandler<Todo> = { todo ->
        completeForm()
    }

    val handleChangeDescription: ChangeEventHandler<HTMLInputElement> = { event ->
        val target = event.target
        val value = target.value
        setTodo(Todo(description = value))
    }


    useEffectOnce {
        fetchTodos()
    }

    h1 {
        +"TODO"
    }

    TodoForm {
        item = todo
        onConfirm = handleConfirmForm
        onDismiss = handleDismissForm
        onChangeDescription = handleChangeDescription
    }

    TodoList {
        items = todos
    }
}
```

### index.html

create an ```index.html``` in ```src/main/resources```

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title></title>
</head>
<body>
<script src="todo-react.js"></script>
</body>
</html>
```

create an empty ```index.css``` in ```src/main/resources```

```css
```

### index.kt

create an ```index.kt``` in ```src/main/kotlin```

```kotlin
import kotlinext.js.require
import kotlinx.browser.document
import react.create
import react.dom.client.createRoot
import todo.control.TodoPage


fun main() {
    require("./index.css")

    val container = document.createElement("div")

    document.body?.appendChild(container)

    createRoot(container).render(TodoPage.create())
}
```
