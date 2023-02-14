package todo.domain.repository

import todo.domain.model.WorkItem
import todo.domain.model.WorkItemsFilter

interface WorkItemRepository {

    fun save(workItem: WorkItem): WorkItem

    fun find(workItem: WorkItemsFilter): List<WorkItem>
}
