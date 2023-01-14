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
import todo.api.TodoApi
import todo.domain.model.Todo


val api = TodoApi()

val mainScope = MainScope()

external interface TodoPageProps : Props

val TodoPage = FC<TodoPageProps> {

    val (todo, setTodo) = useState(Todo("", "", ""))
    val (todos, setTodos) = useState(listOf<Todo>())

    fun dismissForm() {
        setTodo(Todo("", "", ""))
    }

    fun confirmForm() {
        mainScope.launch {
            api.addTodo(todo)
            val todos = api.getTodos()
            setTodo(Todo("", "", ""))
            setTodos(todos)
            window.alert("Todo saved!!")
        }
    }

    fun fetchTodos() {
        mainScope.launch {
            val todos = api.getTodos()
            console.log(JSON.stringify(todos))
            setTodos(todos)
        }
    }


    val handleConfirmForm: ItemEventHandler<Todo> = { confirmForm() }

    val handleDismissForm: ItemEventHandler<Todo> = {  dismissForm() }

    val handleChangeDescription: ChangeEventHandler<HTMLInputElement> = { event ->
        val target = event.target
        val description = target.value
        setTodo(Todo("", "", description))
    }


    useEffectOnce {
        fetchTodos()
    }

    h1 {
        +"TODO"
    }

    TodoForm {
        item = todo
        onConfirm = handleConfirmForm
        onDismiss = handleDismissForm
        onChangeDescription = handleChangeDescription
    }

    TodoList {
        items = todos
    }
}
