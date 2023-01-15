package todo.hibernate.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.springframework.lang.NonNull
import todo.domain.model.Difficulty
import todo.domain.model.Stage
import todo.domain.model.Status
import todo.domain.model.Value
import java.time.LocalDateTime
import java.util.*

@Table(name = "WORK_ITEM")
@Entity(name = "WorkItem")
class WorkItemEntity {

    @Id
    @Column(name = "WORK_ITEM_ID", nullable = false, updatable = false)
    var id: UUID? = null

    @NonNull
    @Column(name = "CREATED", nullable = false, updatable = false)
    var created: LocalDateTime? = LocalDateTime.now()

    @NonNull
    @Column(name = "UPDATED", nullable = false, updatable = false)
    var updated: LocalDateTime? = LocalDateTime.now()

    @NonNull
    @ManyToOne
    @JoinColumn(name = "CREATED_BY_ID", nullable = true, updatable = false)
    var createdBy: UserEntity? = null

    @NonNull
    @ManyToOne
    @JoinColumn(name = "UPDATED_BY_ID", nullable = true, updatable = false)
    var updatedBy: UserEntity? = null

    @NonNull
    @Column(name = "VERSION", nullable = true, updatable = false)
    var version: Int? = null

    @ManyToOne
    @JoinColumn(name = "PREVIOUS_VERSION_ID", nullable = true, updatable = false)
    var previousVersion: WorkItemEntity? = null

    @NonNull
    @ManyToOne
    @JoinColumn(name = "AUTHOR_ID", nullable = true, updatable = false)
    var author: UserEntity? = null

    @ManyToOne
    @JoinColumn(name = "ASSIGNEE_ID", nullable = true, updatable = false)
    var assignee: UserEntity? = null

    @NonNull
    @Column(name = "TITLE", nullable = false, updatable = false)
    var title: String? = null

    @NonNull
    @Column(name = "DESCRIPTION", nullable = true, updatable = false)
    var description: String? = null

    @NonNull
    @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH])
    @JoinTable(
        name = "WORK_ITEM_TAG",
        joinColumns = [JoinColumn(name = "WORK_ITEM_ID")],
        inverseJoinColumns = [JoinColumn(name = "TAG_ID")]
    )
    var tags: List<TagEntity>? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, updatable = false)
    var status: Status? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "VALUE", nullable = true, updatable = false)
    var value: Value? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "DIFFICULTY", nullable = true, updatable = false)
    var difficulty: Difficulty? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "STAGE", nullable = true, updatable = false)
    var stage: Stage? = null

    @ManyToOne
    @JoinColumn(name = "PROJECT_ID", nullable = true, updatable = false)
    var project: ProjectEntity? = null

    @ManyToOne
    @JoinColumn(name = "MILESTONE_ID", nullable = true, updatable = false)
    var milestone: MilestoneEntity? = null
}
