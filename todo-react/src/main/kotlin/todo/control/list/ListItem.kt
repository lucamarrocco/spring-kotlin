package todo.control.list

import csstype.ClassName
import react.FC
import react.Props
import react.dom.html.InputType
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h6
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.small

external interface ListItemProps : Props {
    var info: String?
    var title: String?
    var subtitle: String?
}

val ListItem = FC<ListItemProps> { props ->

    a {
        className = ClassName("list-group-item list-group-item-action d-flex gap-3 py-3 align-items-center")

        input {
            className = ClassName("form-check-input flex-shrink-0")

            type = InputType.checkbox

            value = false
        }

        div {
            className = ClassName("d-flex gap-3 w-100 justify-content-between")

            div {
                h6 {
                    className = ClassName("mb-0")

                    +(props.title ?: "")
                }

                p {
                    className = ClassName("mb-0 opacity-75")

                    +(props.subtitle ?: "")
                }
            }

            small {
                className = ClassName("opacity-50 text-nowrap")

                +(props.info ?: "")
            }
        }
    }
}
