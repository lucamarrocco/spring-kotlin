package todo.domain.model

import kotlinx.serialization.Serializable
import todo.domain.value.Progress

interface SprintReference {

    var id: String?

    var title: String?
}

@Serializable
class Sprint : SprintReference {

    override var id: String? = null

    var created: String? = null
    var createdByUserId: String? = null
    var createdByUserName: String? = null

    var updated: String? = null
    var updatedByUserId: String? = null
    var updatedByUserName: String? = null

    var authorUserId: String? = null
    var authorUserName: String? = null

    override var title: String? = null
    var description: String? = null

    var startDate: String? = null
    var endDate: String? = null

    var status: Progress? = null

    var projectId: String? = null
    var projectName: String? = null

    var workItems: List<WorkItemReference> = mutableListOf()
}
