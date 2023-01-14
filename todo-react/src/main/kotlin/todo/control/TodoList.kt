package todo.control

import react.FC
import react.Props
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ul
import todo.model.Todo

external interface TodoListProps : Props {
    var items: List<Todo>
}

val TodoList = FC<TodoListProps> { props ->
    ul {
        props.items
            .map { todo ->
                todo.description?.let {
                    li {
                        +it
                    }
                }
            }
    }
}
