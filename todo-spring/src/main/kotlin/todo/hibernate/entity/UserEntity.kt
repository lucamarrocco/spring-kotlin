package todo.hibernate.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.lang.NonNull
import java.time.LocalDateTime
import java.util.*

@Table(name = "`USER`")
@Entity(name = "User")
class UserEntity {

    @Id
    @Column(name = "USER_ID", nullable = false, updatable = false)
    var id: UUID? = null

    @NonNull
    @Column(name = "CREATED", nullable = false, updatable = false)
    var created: LocalDateTime? = null

    @NonNull
    @Column(name = "UPDATED", nullable = false, updatable = false)
    var updated: LocalDateTime? = null

    @NonNull
    @Column(name = "NAME", nullable = false, updatable = false)
    var name: String? = null
}
