package todo.domain.repository

import todo.domain.model.WorkItem
import todo.domain.model.WorkItemQuery

interface WorkItemRepository {

    fun save(workItem: WorkItem): WorkItem

    fun find(workItem: WorkItemQuery): List<WorkItem>
}
