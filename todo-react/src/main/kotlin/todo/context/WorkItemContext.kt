package todo.context

import react.FC
import react.Props
import react.ReactNode
import react.createContext
import react.useContext
import react.useState
import todo.client.backend
import todo.domain.model.WorkItem

external interface WorkItemContextCallbacks {
    var newWorkItem: () -> Unit
    var saveWorkItem: suspend () -> Unit
    var dismissWorkItem: () -> Unit
    val setWorkItemTitle: (value: String?) -> Unit
    val setWorkItemDescription: (value: String?) -> Unit
}

external interface WorkItemContextProps : WorkItemContextCallbacks {
    var workItem: WorkItem?
}

class WorkItemContextPropsAdapter(
    // properties
    override var workItem: WorkItem?,
    // callbacks
    override var newWorkItem: () -> Unit,
    override var saveWorkItem: suspend () -> Unit,
    override var dismissWorkItem: () -> Unit,
    override var setWorkItemTitle: (String?) -> Unit,
    override var setWorkItemDescription: (String?) -> Unit
) : WorkItemContextProps

val WorkItemContext = createContext<WorkItemContextProps>()

external interface WorkItemProviderProps : Props {
    var children: ReactNode
}

val WorkItemProvider = FC<WorkItemProviderProps> { props ->

    val (workItem, setWorkItem) = useState<WorkItem?>(null)

    val newWorkItem: () -> Unit = {
        setWorkItem(WorkItem())
    }

    val dismissWorkItem: () -> Unit = {
        setWorkItem(null)
    }

    val saveWorkItem: suspend () -> Unit = {
        workItem?.let { workItem ->
            with(backend) {
                saveWorkItem(workItem)
            }
        }
    }

    val setWorkItemTitle: (value: String?) -> Unit = { value ->
        workItem?.let { setWorkItem(workItem.copy(title = value)) }
    }

    val setWorkItemDescription: (value: String?) -> Unit = { value ->
        workItem?.let { setWorkItem(workItem.copy(description = value)) }
    }

    val context = WorkItemContextPropsAdapter(
        workItem,
        newWorkItem,
        saveWorkItem,
        dismissWorkItem,
        setWorkItemTitle,
        setWorkItemDescription
    )

    WorkItemContext.Provider {
        value = context

        +props.children
    }
}

val useWorkItem = { useContext(WorkItemContext) }
