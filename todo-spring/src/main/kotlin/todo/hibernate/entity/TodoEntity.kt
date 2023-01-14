package todo.hibernate.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.lang.NonNull
import java.time.LocalDateTime
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

    @NonNull
    @Column(name = "CREATED", nullable = false, updatable = false)
    var created: LocalDateTime? = null
}
