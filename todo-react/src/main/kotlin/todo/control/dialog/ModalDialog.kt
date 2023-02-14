package todo.control.dialog

import csstype.ClassName
import org.w3c.dom.HTMLButtonElement
import react.FC
import react.Props
import react.ReactNode
import react.dom.events.MouseEventHandler
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h5

external interface ModalDialogCallbacks {
    var onDialogSaveButtonClick: MouseEventHandler<HTMLButtonElement>
    var onDialogCancelButtonClick: MouseEventHandler<HTMLButtonElement>
}


external interface ModalDialogProps : Props, ModalDialogCallbacks {
    var title: String

    var children: ReactNode
}

val ModalDialog = FC<ModalDialogProps> { props ->

    val handleSaveButtonClick: MouseEventHandler<HTMLButtonElement> = { event ->
        props.onDialogSaveButtonClick(event)
    }

    val handleCancelButtonClick: MouseEventHandler<HTMLButtonElement> = { event ->
        props.onDialogCancelButtonClick(event)
    }

    div {
        className = ClassName("modal show d-block")

        div {
            className = ClassName("modal-dialog")

            div {
                className = ClassName("modal-content")

                div {
                    className = ClassName("modal-header")

                    h5 {
                        className = ClassName("modal-title")

                        +props.title
                    }
                }

                div {
                    className = ClassName("modal-body")

                    +props.children
                }

                div {
                    className = ClassName("modal-footer")

                    button {
                        className = ClassName("btn btn-secondary")

                        +"Discard Changes"

                        onClick = handleCancelButtonClick
                    }

                    button {
                        className = ClassName("btn btn-dark")


                        +"Save Changes"

                        onClick = handleSaveButtonClick
                    }
                }
            }
        }
    }
}
