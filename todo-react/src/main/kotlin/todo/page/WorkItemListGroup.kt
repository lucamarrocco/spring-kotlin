package todo.page

import csstype.ClassName
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import todo.domain.model.WorkItem

external interface WorkItemListGroupProps : Props {
    var items: Array<WorkItem>
}

val WorkItemListGroup = FC<WorkItemListGroupProps> { props ->

    div {
        className = ClassName("list-group w-auto mb-3")

        props.items.map { workItem ->

            WorkItemListItem {

                item = workItem
            }
        }
    }
}
