package todo.control

import csstype.ClassName
import i18n
import react.FC
import react.Props
import react.dom.html.ReactHTML.label
import todo.domain.model.Status


external interface StatusLabelProps : Props {
    var value: Status
}

val StatusLabel = FC<StatusLabelProps> { props ->
    with(props) {
        label {
            htmlFor = "status"
            className = ClassName("label-$htmlFor")
            +i18n(value)
        }
    }
}
