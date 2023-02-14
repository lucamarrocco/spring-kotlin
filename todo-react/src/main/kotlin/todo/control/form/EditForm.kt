package todo.control.form

import csstype.ClassName
import org.w3c.dom.HTMLFormElement
import react.FC
import react.Props
import react.ReactNode
import react.dom.events.FormEventHandler
import react.dom.html.ReactHTML.form

external interface EditFormCallbacks {
    var onFormSubmit: FormEventHandler<HTMLFormElement>
}

external interface EditFormProps : Props, EditFormCallbacks {
    var children: ReactNode
}

val EditForm = FC<EditFormProps> { props ->

    val handleFormSubmit: FormEventHandler<HTMLFormElement> = { event ->
        event.preventDefault()
        event.stopPropagation()
        props.onFormSubmit(event)
    }

    form {
        className = ClassName("")

        onSubmit = handleFormSubmit

        +props.children
    }
}
