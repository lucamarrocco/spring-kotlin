package todo.hibernate.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.springframework.lang.NonNull
import java.time.LocalDateTime
import java.util.*

@Table(name = "PROJECT")
@Entity(name = "Project")
class ProjectEntity {

    @Id
    @Column(name = "PROJECT_ID", nullable = false, updatable = false)
    var id: UUID? = null

    @NonNull
    @Column(name = "CREATED_DATETIME", nullable = false, updatable = false)
    var createdDate: LocalDateTime? = null

    @NonNull
    @Column(name = "UPDATED_DATETIME", nullable = false, updatable = false)
    var updatedDate: LocalDateTime? = null

    @NonNull
    @ManyToOne
    @JoinColumn(name = "CREATED_BY_USER_ID", nullable = false, updatable = false)
    var createdByUser: UserEntity? = null

    @NonNull
    @ManyToOne
    @JoinColumn(name = "UPDATED_BY_USER_ID", nullable = false, updatable = false)
    var updatedByUser: UserEntity? = null

    @NonNull
    @Column(name = "TITLE", nullable = false, updatable = false)
    var title: String? = null

    @NonNull
    @ManyToOne
    @JoinColumn(name = "AUTHOR_USER_ID", nullable = false, updatable = false)
    var authorUser: UserEntity? = null

    @NonNull
    @Column(name = "DESCRIPTION", nullable = false, updatable = false)
    var description: String? = null
}
