package todo

import react.FC
import react.Props
import todo.context.WorkItemProvider
import todo.context.WorkItemsProvider
import todo.page.WorkItemsPage

external interface AppProps : Props {}

val App = FC<AppProps> {
    WorkItemsProvider {
        WorkItemProvider {
            WorkItemsPage {}
        }
    }
}
