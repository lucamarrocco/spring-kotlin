import todo.domain.model.Stage
import todo.domain.model.Status

fun i18n(value: Stage) = when(value) {
    Stage.BACK -> "Backlog"
    Stage.TODO -> "To Do"
    Stage.TEST -> "In Test"
    Stage.VIEW -> "In Review"
    Stage.WORK -> "In Progress"
    Stage.DONE -> "Done!"
}

fun i18n(value: Status) = when(value) {
    Status.OPEN -> "Open"
    Status.CLOSED -> "Closed"
    Status.ARCHIVED -> "Archived"
    Status.DEFERRED -> "Deferred"
}
