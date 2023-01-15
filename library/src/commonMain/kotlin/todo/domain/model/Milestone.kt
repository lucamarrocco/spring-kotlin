package todo.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Milestone(

    val id: String,

    val created: String,
    val createdById: String,
    val createdByName: String,

    val updated: String,
    val updatedById: String,
    val updatedByName: String,

    val authorId: String,
    val authorName: String,

    val name: String,
    val description: String,

    val startDate: String,
    val endDate: String,

    val status: MilestoneStatus
)