package todo.page

import csstype.ClassName
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLFormElement
import org.w3c.dom.HTMLInputElement
import react.FC
import react.Props
import react.dom.events.ChangeEventHandler
import react.dom.events.FormEvent
import react.dom.events.FormEventHandler
import react.dom.events.MouseEvent
import react.dom.html.InputType
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import todo.context.useWorkItem
import todo.control.form.EditForm
import todo.domain.model.WorkItem

typealias TypedFormEventHandler<T> = (event: FormEvent<HTMLFormElement>, it: T) -> Unit

typealias TypedMouseEventHandler<T> = (event: MouseEvent<HTMLButtonElement, *>, it: T) -> Unit

external interface WorkItemEditFormCallbacks {
    var onWorkItemFromSubmit: TypedFormEventHandler<WorkItem>
}

external interface WorkItemEditFormProps : Props, WorkItemEditFormCallbacks {
    var item: WorkItem?
}

val WorkItemEditForm = FC<WorkItemEditFormProps> { props ->

    with(useWorkItem()) {

        val handleFormSubmit: FormEventHandler<HTMLFormElement> = { event ->
            workItem?.let { workItem -> props.onWorkItemFromSubmit(event, workItem) }
        }

        val handleWorkItemTitleChange: ChangeEventHandler<HTMLInputElement> = { event ->
            setWorkItemTitle(event.target.value)
        }

        val handleWorkItemDescriptionChange: ChangeEventHandler<HTMLInputElement> = { event ->
            setWorkItemDescription(event.target.value)
        }

        console.log("work item form :: work item", Json.encodeToString(workItem))

        EditForm {
            onFormSubmit = handleFormSubmit

            div {
                className = ClassName("form-group mb-3")

                label {
                    htmlFor = "title"

                    +"Title"
                }

                input {
                    className = ClassName("form-control")

                    type = InputType.text
                    name = "title"
                    value = workItem?.title ?: ""

                    onChange = handleWorkItemTitleChange
                }
            }

            div {
                className = ClassName("form-group mb-3")

                label {
                    htmlFor = "description"

                    +"Description"
                }

                input {
                    className = ClassName("form-control")

                    type = InputType.text
                    name = "description"
                    value = workItem?.description ?: ""

                    onChange = handleWorkItemDescriptionChange
                }
            }
        }
    }
}
