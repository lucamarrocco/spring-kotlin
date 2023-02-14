package todo.domain.model

import kotlinx.serialization.Serializable
import todo.domain.value.Difficulty
import todo.domain.value.State
import todo.domain.value.Status
import todo.domain.value.Value

@Serializable
data class WorkItemsFilter(

    var text: String? = null,

    var state: MutableList<State> = mutableListOf(),

    var status: MutableList<Status> = mutableListOf(),

    var value: MutableList<Value> = mutableListOf(),

    var difficulty: MutableList<Difficulty> = mutableListOf(),
)
