package domain.control

import Api.addTodo
import Api.findTodos
import Title
import app.control.Dialog
import domain.model.Todo
import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLFormElement
import org.w3c.dom.HTMLInputElement
import react.FC
import react.Props
import react.ReactNode
import react.dom.events.ChangeEventHandler
import react.dom.events.FormEventHandler
import react.dom.events.MouseEventHandler
import react.dom.html.ReactHTML.fieldset
import react.dom.html.ReactHTML.form
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.useEffectOnce
import react.useState


val mainScope = MainScope()

external interface TodoPageProps : Props

val TodoPage = FC<TodoPageProps> {

    val (todo, setTodo) = useState(Todo())
    val (todos, setTodos) = useState<List<Todo>>(emptyList())
    val (isTodoFormOpen, setTodoFormOpen) = useState(false)


    fun presentTodoForm() {
        setTodo(Todo())
        setTodoFormOpen(true)
    }

    fun dismissTodoForm() {
        setTodo(Todo())
        setTodoFormOpen(false)
    }

    fun completeTodoForm() {
        if (null == todo.description) return

        mainScope.launch {
            addTodo(todo)
            setTodos(findTodos())
            dismissTodoForm()
            window.alert("Todo saved!!")
        }
    }


    fun updateTodos() {
        mainScope.launch {
            setTodos(findTodos())
        }
    }


    val handlePresentForm: MouseEventHandler<HTMLButtonElement> = {
        presentTodoForm()
    }

    val handleDismissForm: MouseEventHandler<HTMLButtonElement> = {
        dismissTodoForm()
    }

    val handleCompleteForm: MouseEventHandler<HTMLButtonElement> = {
        completeTodoForm()
    }

    val handleSubmitForm: FormEventHandler<HTMLFormElement> = { event ->
        event.preventDefault()
        completeTodoForm()
    }

    val handleChangeDescription: ChangeEventHandler<HTMLInputElement> = { event ->
        val target = event.target
        val value = target.value
        setTodo(Todo(description = value))
    }


    useEffectOnce {
        updateTodos()
    }


    Title {
        title = "Todo"

        actionLabel = "Add"
        actionDisabled = isTodoFormOpen
        actionHandleClick = handlePresentForm
    }

    TodoList {
        items = todos
    }

    Dialog {
        open = isTodoFormOpen

        onSave = handleCompleteForm
        onCancel = handleDismissForm

        body = form {
            fieldset {
                label {
                    input {
                        name = "description"
                        value = todo.description ?: ""
                        onChange = handleChangeDescription
                    }
                }

            }
            onSubmit = handleSubmitForm
        }.unsafeCast<ReactNode>()
    }
}
