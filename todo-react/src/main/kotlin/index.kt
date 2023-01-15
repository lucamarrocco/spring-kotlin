import kotlinext.js.require
import kotlinx.browser.document
import react.create
import react.dom.client.createRoot
import todo.control.WorkItemPage


fun main() {
    require("./index.css")

    val container = document.createElement("div")

    document.body?.appendChild(container)

    createRoot(container).render(WorkItemPage.create())
}
