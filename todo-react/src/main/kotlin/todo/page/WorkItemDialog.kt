import org.w3c.dom.HTMLButtonElement
import react.FC
import react.Props
import react.dom.events.MouseEventHandler
import todo.control.dialog.ModalDialog
import todo.domain.model.WorkItem
import todo.page.TypedFormEventHandler
import todo.page.TypedMouseEventHandler
import todo.page.WorkItemEditForm

external interface WorkItemDialogCallbacks {
    var onWorkItemFromSubmit: TypedFormEventHandler<WorkItem>
    var onWorkItemSaveButtonClick: TypedMouseEventHandler<WorkItem>
    var onWorkItemCancelButtonClick: TypedMouseEventHandler<WorkItem>
}

external interface WorkItemDialogProps : Props, WorkItemDialogCallbacks {
    var item: WorkItem
}

val WorkItemDialog = FC<WorkItemDialogProps> { props ->

    val handleDialogSaveButtonClick: MouseEventHandler<HTMLButtonElement> = { event ->
        props.onWorkItemSaveButtonClick(event, props.item)
    }

    val handleDialogCancelButtonClick: MouseEventHandler<HTMLButtonElement> = { event ->
        props.onWorkItemCancelButtonClick(event, props.item)
    }

    ModalDialog {
        title = "Work Item"

        onDialogSaveButtonClick = handleDialogSaveButtonClick
        onDialogCancelButtonClick = handleDialogCancelButtonClick

        WorkItemEditForm {
            item = props.item

            onWorkItemFromSubmit = props.onWorkItemFromSubmit
        }
    }
}
