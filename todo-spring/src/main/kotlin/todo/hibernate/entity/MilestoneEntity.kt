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
import java.time.LocalDateTime
import java.util.*

@Table(name = "MILESTONE")
@Entity(name = "Milestone")
class MilestoneEntity {

    @Id
    @Column(name = "MILESTONE_ID", nullable = false, updatable = false)
    var id: UUID? = null

    @NonNull
    @Column(name = "CREATED", nullable = false, updatable = false)
    var created: LocalDateTime? = null

    @NonNull
    @Column(name = "UPDATED", nullable = false, updatable = false)
    var updated: LocalDateTime? = null

    @NonNull
    @ManyToOne
    @JoinColumn(name = "CREATED_BY_ID", nullable = false, updatable = false)
    var createdBy: UserEntity? = null

    @NonNull
    @ManyToOne
    @JoinColumn(name = "UPDATED_BY_ID", nullable = false, updatable = false)
    var updatedBy: UserEntity? = null

    @NonNull
    @Column(name = "NAME", nullable = false, updatable = false)
    var name: String? = null

    @NonNull
    @ManyToOne
    @JoinColumn(name = "AUTHOR_ID", nullable = false, updatable = false)
    var author: UserEntity? = null

    @NonNull
    @Column(name = "DESCRIPTION", nullable = false, updatable = false)
    var description: String? = null

    @NonNull
    @Column(name = "START_DATE", nullable = false, updatable = false)
    var startDate: LocalDateTime? = null

    @NonNull
    @Column(name = "END_DATE", nullable = false, updatable = false)
    var endDate: LocalDateTime? = null

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, updatable = false)
    var status: MilestoneStatus? = null
}
