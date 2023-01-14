package todo.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Todo(

    val id: String,

    val created: String,

    val description: String,
)
