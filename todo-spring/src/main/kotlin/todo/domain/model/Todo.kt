package todo.domain.model

import java.time.LocalDateTime
import java.util.*

data class Todo(

    val id: UUID?,

    val description: String,

    val created: LocalDateTime?
)
