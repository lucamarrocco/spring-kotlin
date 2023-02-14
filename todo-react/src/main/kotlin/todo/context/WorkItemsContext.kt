package todo.context

import kotlinx.coroutines.launch
import react.FC
import react.Props
import react.ReactNode
import react.createContext
import react.useContext
import react.useState
import todo.client.backend
import todo.client.mainScope

import todo.domain.model.WorkItem
import todo.domain.model.WorkItemsFilter


external interface WorkItemsContextCallbacks {
    var search: () -> Unit
    val setSearchText: (value: String?) -> Unit
}

external interface WorkItemsContextProps : WorkItemsContextCallbacks {
    var workItems: Array<WorkItem>
    var filter: WorkItemsFilter
}

class WorkItemsContextPropsAdapter(
    // properties
    override var workItems: Array<WorkItem>,
    override var filter: WorkItemsFilter,
    // callbacks
    override var search: () -> Unit,
    override val setSearchText: (value: String?) -> Unit
) : WorkItemsContextProps

val WorkItemsContext = createContext<WorkItemsContextProps>()

external interface WorkItemsProviderProps : Props {
    var children: ReactNode
}

val WorkItemsProvider = FC<WorkItemsProviderProps> { props ->
    val (workItems, setWorkItems) = useState(arrayOf<WorkItem>())
    val (workItemsFilter, setWorkItemsFilter) = useState(WorkItemsFilter())

    val search: () -> Unit = { ->
        mainScope.launch {
            setWorkItems(backend.listWorkItems(workItemsFilter))
        }
    }

    val setSearchText: (value: String?) -> Unit = { value ->
        setWorkItemsFilter(workItemsFilter.copy(text = value))
    }

    val context = WorkItemsContextPropsAdapter(
        workItems,
        workItemsFilter,
        search,
        setSearchText,
    )

    WorkItemsContext.Provider {
        value = context

        +props.children
    }
}

val useWorkItems = { useContext(WorkItemsContext) }
