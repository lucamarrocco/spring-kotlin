package todo.domain.model

import kotlinx.serialization.Serializable

@Serializable
class WorkItemQuery {
    var text: String? = null
}
