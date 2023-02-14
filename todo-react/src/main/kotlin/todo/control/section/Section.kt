package todo.control.section

import csstype.ClassName
import react.FC
import react.Props
import react.ReactNode
import react.dom.html.ReactHTML.div

external interface SectionProps : Props {
    var title: String
    var children: ReactNode
}

val Section = FC<SectionProps> { props ->

    div {
        className = ClassName("mb-3 p-3 bg-white rounded box-shadow border")

        SectionHeader {
            title = props.title
        }

        +props.children
    }
}
