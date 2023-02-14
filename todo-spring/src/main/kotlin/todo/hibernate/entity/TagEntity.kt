package todo.hibernate.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.springframework.lang.NonNull
import java.time.LocalDateTime
import java.util.*

@Table(name = "TAG")
@Entity(name = "Tag")
class TagEntity {

    @Id
    @Column(name = "TAG_ID", nullable = false, updatable = false)
    var id: UUID? = null

    @NonNull
    @Column(name = "CREATED_DATETIME", nullable = false, updatable = false)
    var createdDateTime: LocalDateTime? = null

    @NonNull
    @Column(name = "UPDATED_DATETIME", nullable = false, updatable = false)
    var updatedDateTime: LocalDateTime? = null

    @NonNull
    @ManyToOne
    @JoinColumn(name = "AUTHOR_ID", nullable = false, updatable = false)
    var authorUser: UserEntity? = null

    @NonNull
    @Column(name = "DESCRIPTION", nullable = false, updatable = false)
    var description: String? = null

    @ManyToMany(
        mappedBy = "tags",
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH]
    )
    private val workItems: List<WorkItemEntity>? = null
}
