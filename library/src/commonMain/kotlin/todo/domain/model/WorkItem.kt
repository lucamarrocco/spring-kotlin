package todo.domain.model

import kotlinx.serialization.Serializable

@Serializable
class WorkItem {

    var id: String? = null

    // when this entity has been originally created
    var created: String? = null

    // reference to the user that changed this work item recently
    var createdById: String? = null
    var createdByName: String? = null

    // when this entity has been recently updated
    var updated: String? = null

    // reference to the user that changed this work item recently
    var updatedById: String? = null
    var updatedByName: String? = null

    // a number that is incremented every time this item has an atomic change
    var version: Int? = null

    // a reference to previous version of this entity
    var previousVersionId: String? = null

    // name of the person that own this item, usually is the responsible of this item but it could be another person
    var ownerId: String? = null
    var ownerName: String? = null

    // name of the person that originally created this item or the name of the person that recently changed this item
    var authorId: String? = null
    var authorName: String? = null

    // name of the person whom is responsible to accomplish this activity, it could be different from the author
    var assigneeId: String? = null
    var assigneeName: String? = null

    // a brief description, usually this fit a line in A4 sheet, that describe this item in simple and easy manner
    var title: String? = null

    // a long description, usually it include multiple things such as images, code snippet, etc
    var description: String? = null

    // a list of key words, either from a controlled or from a non controlled vocubolary that let users to organize items
    var tags: List<Tag> = listOf()

    // a particular status in a simple workflow that let essentially to open/close items or to defer/archive them in future
    var status: Status? = null

    // an estimation in terms of value either high or low for this item. Note: this is used to build a 2-d board using Haisenower matrix
    var value: Value? = null

    // an estimation in terms of complexity either high or low for this item. Note: this is used to build a 2-d board using Haisenower matrix
    var difficulty: Difficulty? = null

    // a name of the stage of the production system, tipically is "To Do", "In Progress", "Completed"
    var stage: Stage? = Stage.BACK

    // a reference of a project which contains this item. is used to group items and divide them per project
    var projectId: String? = null
    var projectName: String? = null

    // a reference of a milestone which contains this item. is used to group items and divide them per milestone
    var milestoneId: String? = null
    var milestoneName: String? = null
}

fun backlogOpen(titleUpdated: String? = null) = with(WorkItem()) {
    title = titleUpdated
    stage = Stage.BACK
    status = Status.OPEN
    this
}
