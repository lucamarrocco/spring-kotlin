package todo.control

import csstype.ClassName
import i18n
import react.FC
import react.Props
import react.dom.html.ReactHTML.label
import todo.domain.model.Stage


external interface StageLabelProps : Props {
    var value: Stage
}

val StageLabel = FC<StageLabelProps> { props ->
    with(props) {
        label {
            htmlFor = "stage"
            className = ClassName("label-$htmlFor")
            +i18n(value)
        }
    }
}
