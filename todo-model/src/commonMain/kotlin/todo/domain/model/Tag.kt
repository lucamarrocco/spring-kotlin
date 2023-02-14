package todo.domain.model

import kotlinx.serialization.Serializable

interface TagReference {

    var id: String?

    var title: String?
}

@Serializable
class Tag : TagReference {

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
}
