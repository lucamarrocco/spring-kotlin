package todo.control

import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.w3c.dom.HTMLInputElement
import react.FC
import react.Props
import react.dom.events.ChangeEventHandler
import react.dom.html.ReactHTML.h1
import react.useEffectOnce
import react.useState
import todo.api.WorkApi
import todo.domain.model.WorkItem
import todo.domain.model.WorkItemQuery
import todo.domain.model.backlogOpen


val api = WorkApi()

val mainScope = MainScope()

external interface WorkItemPageProps : Props

val WorkItemPage = FC<WorkItemPageProps> {

    val (workItem, setWorkItem) = useState(backlogOpen())
    val (workItems, setWorkItems) = useState(listOf<WorkItem>())
    val (workItemQuery, setWorkItemQuery) = useState(WorkItemQuery())

    fun dismissWorkItem() = mainScope.launch {
        setWorkItem(backlogOpen())
    }

    fun saveWorkItem() {
        mainScope.launch {
            api.save(workItem)
            setWorkItem(backlogOpen())
            setWorkItems(api.find(workItemQuery))
            window.alert("Work Item Saved!!")
        }
    }

    fun search() {
        mainScope.launch {
            setWorkItems(api.find(workItemQuery))
        }
    }

    fun searchReset() {
        mainScope.launch {
            setWorkItemQuery(WorkItemQuery())
            setWorkItems(api.find(workItemQuery))
        }
    }

    val handleSearchReset: ItemEventHandler<WorkItemQuery> = { searchReset() }

    val handleSearchSubmit: ItemEventHandler<WorkItemQuery> = { search() }

    val handleWorkItemChangeTitle: ChangeEventHandler<HTMLInputElement> = { event ->
        val target = event.target
        val title = target.value
        setWorkItem(backlogOpen(title))
    }


    val handleWorkItemConfirm: ItemEventHandler<WorkItem> = { saveWorkItem() }

    val handleWorkItemDismiss: ItemEventHandler<WorkItem> = { dismissWorkItem() }

    val handleSearchChangeText: ChangeEventHandler<HTMLInputElement> = { event ->
        val target = event.target

        setWorkItemQuery(with(WorkItemQuery()) {
            text = target.value
            this
        })
    }

    useEffectOnce {
        mainScope.launch {
            setWorkItems(api.find(workItemQuery))
        }
    }

    h1 {
        +"TODO"
    }

    SearchForm {
        model = workItemQuery
        onReset = handleSearchReset
        onSearch = handleSearchSubmit
        onChangeText = handleSearchChangeText
    }

    WorkItemForm {
        model = workItem
        onConfirm = handleWorkItemConfirm
        onDismiss = handleWorkItemDismiss
        onChangeTitle = handleWorkItemChangeTitle
    }

    WorkItemList {
        items = workItems
    }
}
