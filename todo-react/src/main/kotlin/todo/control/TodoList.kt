package domain.control

import domain.model.Todo
import react.FC
import react.Props
import react.dom.html.ReactHTML.div

external interface TodoListProps : Props {
    var items: List<Todo>
}

external interface TodoListItemProps : Props {
    var item: Todo
}

val TodoList = FC<TodoListProps> { props ->
    props.items
        .map {
            TodoListItem {
                item = it
            }
        }
}

val TodoListItem = FC<TodoListItemProps> { props ->
    val item = props.item

    val text = item.description ?: ""

    div {
        +text
    }
}
