package todo.control

import org.w3c.dom.HTMLInputElement
import react.FC
import react.Props
import react.dom.events.ChangeEventHandler
import react.dom.html.ButtonType
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.form
import react.dom.html.ReactHTML.input
import todo.domain.model.WorkItem

typealias ItemEventHandler<T> = (item: T) -> Unit
typealias DismissEventHandler<T> = ItemEventHandler<T>
typealias ConfirmEventHandler<T> = ItemEventHandler<T>


external interface WorkItemFormProps : Props {
    var model: WorkItem

    var onDismiss: DismissEventHandler<WorkItem>
    var onConfirm: ConfirmEventHandler<WorkItem>
    var onChangeTitle: ChangeEventHandler<HTMLInputElement>
}

val WorkItemForm = FC<WorkItemFormProps> { props ->
    with(props) {
        form {
            onSubmit = { event ->
                event.preventDefault()
                onConfirm(model)
            }

            input {
                autoFocus = true
                name = "title"
                value = model.title ?: ""
                onChange = onChangeTitle
            }

            button {
                +"Save"
                type = ButtonType.submit
                onClick = { event ->
                    event.preventDefault()
                    onConfirm(model)
                }
            }

            button {
                +"Cancel"
                type = ButtonType.reset
                onClick = { event ->
                    event.preventDefault()
                    onDismiss(model)
                }
            }
        }
    }
}
