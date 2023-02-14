package todo.control.section

import csstype.ClassName
import org.w3c.dom.HTMLButtonElement
import react.FC
import react.Props
import react.dom.events.MouseEventHandler
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.header
import react.dom.html.ReactHTML.span

external interface PageHeaderCallbacks {
    var onNew: MouseEventHandler<HTMLButtonElement>?
}

external interface PageHeaderProps : Props, PageHeaderCallbacks {
    var title: String
}

val PageHeader = FC<PageHeaderProps> { props ->

    header {
        className = ClassName("py-3")

        div {
            className = ClassName("container d-flex flex-wrap justify-content-start")

            a {
                className = ClassName("d-flex align-items-center mb-lg-0 me-lg-auto text-dark text-decoration-none")

                span {
                    className = ClassName("fs-4")

                    +props.title
                }

                props.onNew?.let { onNew ->

                    button {
                        className = ClassName("btn btn-dark btn-sm mx-2")

                        +"New"

                        onClick = onNew
                    }
                }
            }
        }
    }
}
