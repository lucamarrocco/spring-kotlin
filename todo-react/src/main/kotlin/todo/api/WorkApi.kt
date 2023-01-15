package todo.api

import todo.domain.model.WorkItem
import todo.domain.model.WorkItemQuery

class WorkApi : RestApi("http://localhost:8080") {

    suspend fun save(item: WorkItem): WorkItem = post("work-item", item)

    suspend fun find(query: WorkItemQuery): List<WorkItem> = get("work-item", query)
}
