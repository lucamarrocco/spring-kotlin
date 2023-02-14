package todo.hibernate.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.springframework.lang.NonNull
import todo.domain.value.Status
import java.time.LocalDateTime
import java.util.*

@Table(name = "MILESTONE")
@Entity(name = "Milestone")
class MilestoneEntity {

    @Id
    @Column(name = "MILESTONE_ID", nullable = false, updatable = false)
    var id: UUID? = null

    @NonNull
    @Column(name = "CREATED_DATETIME", nullable = false, updatable = false)
    var createdDatetime: LocalDateTime? = null

    @NonNull
    @Column(name = "UPDATED_DATETIME", nullable = false, updatable = false)
    var updatedDatetime: LocalDateTime? = null

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
    @JoinColumn(name = "AUTHOR_ID", nullable = false, updatable = false)
    var authorUser: UserEntity? = null

    @NonNull
    @Column(name = "DESCRIPTION", nullable = false, updatable = false)
    var description: String? = null

    @NonNull
    @Column(name = "START_DATETIME", nullable = false, updatable = false)
    var startDateTime: LocalDateTime? = null

    @NonNull
    @Column(name = "END_DATETIME", nullable = false, updatable = false)
    var endDateTime: LocalDateTime? = null

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, updatable = false)
    var status: Status? = null
}
