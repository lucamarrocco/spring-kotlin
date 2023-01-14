package todo.control

import org.w3c.dom.HTMLInputElement
import react.FC
import react.Props
import react.dom.events.ChangeEventHandler
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.form
import react.dom.html.ReactHTML.input
import todo.model.Todo

typealias ItemEventHandler<T> = (item: T) -> Unit
typealias DismissEventHandler<T> = ItemEventHandler<T>
typealias ConfirmEventHandler<T> = ItemEventHandler<T>


external interface TodoFormProps : Props {
    var item: Todo

    var onDismiss: DismissEventHandler<Todo>
    var onConfirm: ConfirmEventHandler<Todo>

    var onChangeDescription: ChangeEventHandler<HTMLInputElement>
}

val TodoForm = FC<TodoFormProps> { props ->
    form {
        input {
            autoFocus = true
            name = "description"
            value = props.item.description ?: ""
            onChange = props.onChangeDescription
        }

        button {
            +"Cancel"
            onClick = { props.onDismiss(props.item) }
        }

        button {
            +"Save"
            onClick = { props.onConfirm(props.item) }
        }

        onSubmit = { event -> event.preventDefault(); props.onConfirm(props.item) }
    }
}


