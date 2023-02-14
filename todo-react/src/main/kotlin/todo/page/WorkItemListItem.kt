package todo.page

import react.FC
import react.Props
import todo.control.list.ListItem
import todo.domain.model.WorkItem


external interface WorkItemListItemProps : Props {
    var item: WorkItem
}

val WorkItemListItem = FC<WorkItemListItemProps> { props ->

    ListItem {
        info = getInfo(props.item)
        title = getTitle(props.item)
        subtitle = getSubtitle(props.item)
    }
}


private fun getInfo(item: WorkItem): String? = with(item) {
    updated
}

private fun getTitle(item: WorkItem): String = with(item) {
    listOf("#${number}", title).joinToString(" ")
}

private fun getSubtitle(item: WorkItem): String? = with(item) {
    description
}

