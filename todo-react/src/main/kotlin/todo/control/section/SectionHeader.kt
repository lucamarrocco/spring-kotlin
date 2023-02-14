package todo.control.section

import csstype.ClassName
import react.FC
import react.Props
import react.dom.html.ReactHTML.h6

external interface SectionHeaderProps : Props {
    var title: String
}

val SectionHeader = FC<SectionHeaderProps> { props ->

    h6 {
        className = ClassName("border-bottom border-gray pb-3 mb-0")

        +props.title
    }
}
