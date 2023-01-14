package todo.control

import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLFormElement
import org.w3c.dom.HTMLInputElement
import react.FC
import react.Fragment
import react.Props
import react.dom.events.ChangeEventHandler
import react.dom.events.FormEventHandler
import react.dom.events.MouseEventHandler
import react.dom.html.ReactHTML.h1
import react.useEffectOnce
import react.useState
import todo.api.TodoApi
import todo.model.Todo


val api = TodoApi()

val mainScope = MainScope()

external interface TodoPageProps : Props

val TodoPage = FC<TodoPageProps> {

    val (todo, setTodo) = useState(Todo())
    val (todos, setTodos) = useState(listOf<Todo>())

    fun dismissForm() {
        setTodo(Todo())
    }

    fun completeForm() {
        if (null == todo.description) return

        mainScope.launch {
            api.addTodo(todo)
            setTodo(Todo())
            setTodos(api.getTodos())
            window.alert("Todo saved!!")
        }
    }

    fun fetchTodos() {
        mainScope.launch {
            setTodos(api.getTodos())
        }
    }


    val handleDismissForm: ItemEventHandler<Todo> = { todo ->
        dismissForm()
    }

    val handleConfirmForm: ItemEventHandler<Todo> = { todo ->
        completeForm()
    }

    val handleChangeDescription: ChangeEventHandler<HTMLInputElement> = { event ->
        val target = event.target
        val value = target.value
        setTodo(Todo(description = value))
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
