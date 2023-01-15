package todo.control

import org.w3c.dom.HTMLInputElement
import react.FC
import react.Props
import react.dom.events.ChangeEventHandler
import react.dom.html.ButtonType
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.form
import react.dom.html.ReactHTML.input
import todo.domain.model.WorkItemQuery


external interface SearchFormProps : Props {
    var model: WorkItemQuery

    var onReset: ConfirmEventHandler<WorkItemQuery>
    var onSearch: ConfirmEventHandler<WorkItemQuery>
    var onChangeText: ChangeEventHandler<HTMLInputElement>
}

val SearchForm = FC<SearchFormProps> { props ->
    with(props) {
        form {
            onSubmit = { event ->
                event.preventDefault()
                onSearch(model)
            }

            input {
                autoFocus = true
                name = "text"
                value = model.text ?: ""
                onChange = onChangeText
            }

            button {
                +"Search"
                type = ButtonType.submit
                onClick = { event ->
                    event.preventDefault()
                    onSearch(model)
                }
            }

            button {
                +"Reset"
                type = ButtonType.reset
                onClick = { event ->
                    event.preventDefault()
                    onReset(model)
                }
            }
        }
    }
}
