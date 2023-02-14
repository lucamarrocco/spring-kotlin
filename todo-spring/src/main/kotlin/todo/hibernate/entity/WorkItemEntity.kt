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
import todo.domain.value.Difficulty
import todo.domain.value.State
import todo.domain.value.Status
import todo.domain.value.Value
import java.time.LocalDateTime
import java.util.*

@Table(name = "WORK_ITEM")
@Entity(name = "WorkItem")
class WorkItemEntity {

    @Id
    @Column(name = "WORK_ITEM_ID", nullable = false, updatable = false)
    var id: UUID? = null

    @NonNull
    @Column(name = "CREATED_DATETIME", nullable = false, updatable = false)
    var createdDateTime: LocalDateTime? = LocalDateTime.now()

    @NonNull
    @Column(name = "UPDATED_DATETIME", nullable = false, updatable = false)
    var updatedDateTime: LocalDateTime? = LocalDateTime.now()

    @ManyToOne
    @JoinColumn(name = "CREATED_BY_USER_ID", nullable = true, updatable = false)
    var createdByUser: UserEntity? = null

    @ManyToOne
    @JoinColumn(name = "UPDATED_BY_USER_ID", nullable = true, updatable = false)
    var updatedByUser: UserEntity? = null

    @NonNull
    @Column(name = "VERSION", nullable = false, updatable = false)
    var version: Int = 0

    @ManyToOne
    @JoinColumn(name = "PREVIOUS_VERSION_ID", nullable = true, updatable = false)
    var previousVersion: WorkItemEntity? = null

    @ManyToOne
    @JoinColumn(name = "AUTHOR_USER_ID", nullable = true, updatable = false)
    var authorUser: UserEntity? = null

    @ManyToOne
    @JoinColumn(name = "ASSIGNEE_USER_ID", nullable = true, updatable = false)
    var assigneeUser: UserEntity? = null

    @NonNull
    @Column(name = "TITLE", nullable = false, updatable = false)
    var title: String? = null

    @Column(name = "NUMBER", unique = true, nullable = false, updatable = false)
    var number: Int? = null

    @Column(name = "DESCRIPTION", nullable = true, updatable = false)
    var description: String? = null

    @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH])
    @JoinTable(
        name = "WORK_ITEM_TAG",
        joinColumns = [JoinColumn(name = "WORK_ITEM_ID")],
        inverseJoinColumns = [JoinColumn(name = "TAG_ID")]
    )
    var tags: List<TagEntity>? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = true, updatable = false)
    var status: Status? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "VALUE", nullable = true, updatable = false)
    var value: Value? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "DIFFICULTY", nullable = true, updatable = false)
    var difficulty: Difficulty? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "STATE", nullable = true, updatable = false)
    var state: State? = null

    @ManyToOne
    @JoinColumn(name = "PROJECT_ID", nullable = true, updatable = false)
    var project: ProjectEntity? = null

    @ManyToOne
    @JoinColumn(name = "MILESTONE_ID", nullable = true, updatable = false)
    var milestone: MilestoneEntity? = null
}
