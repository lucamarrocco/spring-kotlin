package todo.domain.model

import kotlinx.serialization.Serializable
import todo.domain.value.Difficulty
import todo.domain.value.State
import todo.domain.value.Status
import todo.domain.value.Value

interface WorkItemReference {

    var id: String?

    var title: String?
}

@Serializable
data class WorkItem(

    override var id: String? = null,

    // an incremental number that identify this work item within a project
    var number: Int? = null,

    // when this entity has been originally created
    var created: String? = null,

    // reference to the user that changed this work item recently
    var createdByUserId: String? = null,
    var createdByUserName: String? = null,

    // when this entity has been recently updated
    var updated: String? = null,

    // reference to the user that changed this work item recently
    var updatedByUserId: String? = null,
    var updatedByUserName: String? = null,

    // a number that is incremented every time this item has an atomic change
    var version: Int? = null,

    // a reference to previous version of this entity
    var previousVersionId: String? = null,

    // name of the person that own this item, usually is the responsible of this item but it could be another person
    var ownerUserId: String? = null,
    var ownerUserName: String? = null,

    // name of the person that originally created this item or the name of the person that recently changed this item
    var authorUserId: String? = null,
    var authorUserName: String? = null,

    // name of the person whom is responsible to accomplish this activity, it could be different from the author
    var assigneeUserId: String? = null,
    var assigneeUserName: String? = null,

    // a brief description, usually this fit a line in A4 sheet, that describe this item in simple and easy manner
    override var title: String? = null,

    // a long description, usually it include multiple things such as images, code snippet, etc
    var description: String? = null,

    // a list of key words, either from a controlled or from a non controlled vocubolary that let users to organize items
    var tags: List<Tag> = listOf(),

    // a name of the stage of the production system, tipically is "To Do", "In Progress", "Completed"
    var state: State? = State.BACKLOG,

    // a particular status in a simple workflow that let essentially to open/close items or to defer/archive them in future
    var status: Status? = Status.OPEN,

    // an estimation in terms of value either high or low for this item. Note: this is used to build a 2-d board using Haisenower matrix
    var value: Value? = null,

    // an estimation in terms of complexity either high or low for this item. Note: this is used to build a 2-d board using Haisenower matrix
    var difficulty: Difficulty? = null,

    // a reference of a project which contains this item. is used to group items and divide them per project
    var projectId: String? = null,
    var projectName: String? = null,

    // a reference of a sprint which contains this item. is used to group items and divide them per sprint
    var sprintId: String? = null,
    var sprintName: String? = null,

    // a reference of a milestone which contains this item. is used to group items and divide them per milestone
    var milestoneId: String? = null,
    var milestoneName: String? = null,

    ) : WorkItemReference
