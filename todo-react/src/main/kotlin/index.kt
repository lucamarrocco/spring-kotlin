import kotlinx.browser.document

import react.create
import react.dom.client.createRoot

import todo.App


fun main() {
    val container = document.createElement("div")

    document.body?.let { documentBody ->

        documentBody.appendChild(container)

        createRoot(container).render(App.create())
    }
}
