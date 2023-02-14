package todo.page

import WorkItemDialog
import kotlinx.browser.window
import kotlinx.coroutines.launch
import kotlinx.js.timers.setTimeout
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLFormElement
import react.FC
import react.Props
import react.dom.events.FormEventHandler
import react.dom.events.MouseEventHandler
import react.useEffect
import todo.client.mainScope
import todo.context.useWorkItem
import todo.context.useWorkItems
import todo.control.form.SearchForm
import todo.control.section.MainContainer
import todo.control.section.PageHeader
import todo.domain.model.WorkItem

external interface WorkItemsPageProps : Props

val WorkItemsPage = FC<WorkItemsPageProps> {

    with(useWorkItems()) {

        with(useWorkItem()) {

            val handleSearchFormSubmit: FormEventHandler<HTMLFormElement> = { event ->
                search()
            }

            val handleNewButtonClick: MouseEventHandler<HTMLButtonElement> = { event ->
                newWorkItem()
            }

            val handleWorkItemFormSubmit: TypedFormEventHandler<WorkItem> = { event, workItem ->
                dismissWorkItem()
            }

            val handleWorkItemSaveButtonClick: TypedMouseEventHandler<WorkItem> = { event, workItem ->
                mainScope.launch {
                    saveWorkItem()
                    dismissWorkItem()
                    setTimeout({ search() }, 100)
                    setTimeout({ window.alert("Saved!") }, 100)
                }
            }

            val handleWorkItemCancelButtonClick: TypedMouseEventHandler<WorkItem> = { event, workItem ->
                dismissWorkItem()
            }


            useEffect(filter.text) {
                search()
            }


            PageHeader {
                title = "Work Items"

                onNew = handleNewButtonClick
            }

            MainContainer {

                SearchForm {
                    placeholder = "search..."

                    onSearchFormSubmit = handleSearchFormSubmit
                }

                WorkItemListGroup {
                    items = workItems
                }

                workItem?.let { workItem ->

                    WorkItemDialog {
                        item = workItem

                        onWorkItemFromSubmit = handleWorkItemFormSubmit
                        onWorkItemSaveButtonClick = handleWorkItemSaveButtonClick
                        onWorkItemCancelButtonClick = handleWorkItemCancelButtonClick
                    }
                }
            }
        }
    }
}

