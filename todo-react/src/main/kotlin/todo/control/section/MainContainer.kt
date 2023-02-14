package todo.control.section

import csstype.ClassName
import react.FC
import react.Props
import react.ReactNode
import react.dom.html.ReactHTML.main

external interface MainContainerProps : Props {
    val children: ReactNode
}

val MainContainer = FC<MainContainerProps> { props ->

    main {
        className = ClassName("container")

        +props.children
    }
}
