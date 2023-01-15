package todo.control

import csstype.ClassName
import react.FC
import react.Props
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.span
import react.dom.html.ReactHTML.ul
import todo.domain.model.WorkItem

external interface WorkItemListProps : Props {
    var items: List<WorkItem>
}

val WorkItemList = FC<WorkItemListProps> { props ->
    ul {
        className = ClassName("list")

        props.items
            .map { workItem ->
                with(workItem) {
                    li {
                        className = ClassName("list-item")

                        stage?.let { stage -> StageLabel { value = stage } }
                        status?.let { status -> StatusLabel { value = status } }

                        span {
                            className = ClassName("title")
                            +(workItem.title ?: "")
                        }
                    }
                }
            }
    }
}
