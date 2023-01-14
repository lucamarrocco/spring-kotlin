package todo.model

import kotlinx.serialization.Serializable


@Serializable
data class Todo(

    val id: String? = null,

    val description: String? = null,

    val created: String? = null
)
