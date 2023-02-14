package todo.control.form

import csstype.ClassName
import org.w3c.dom.HTMLFormElement
import org.w3c.dom.HTMLInputElement
import react.FC
import react.Props
import react.dom.events.ChangeEventHandler
import react.dom.events.FormEventHandler
import react.dom.html.InputType
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.form
import react.dom.html.ReactHTML.input
import todo.context.useWorkItems

external interface SearchFormCallbacks {
    var onSearchFormSubmit: FormEventHandler<HTMLFormElement>
}

external interface SearchFormProps : Props, SearchFormCallbacks {
    var placeholder: String?
}

val SearchForm = FC<SearchFormProps> { props ->

    with(useWorkItems()) {

        val handleFormSubmit: FormEventHandler<HTMLFormElement> = { event ->
            event.preventDefault()
            props.onSearchFormSubmit(event)
        }

        val handleTextChange: ChangeEventHandler<HTMLInputElement> = { event ->
            setSearchText(event.target.value);
        }

        div {
            className = ClassName("mb-3")

            form {
                onSubmit = handleFormSubmit

                input {
                    className = ClassName("form-control")

                    value = filter.text ?: ""

                    type = InputType.search
                    placeholder = props.placeholder

                    onChange = handleTextChange
                }
            }
        }
    }
}
