## Upgrade Model With More Attributes and Implement a Search

Current Todo model is very simple and minimal, at the moment there is a simple description. Todo entity are immutable
hence there is not the possibility to update an item after is creationg. The aim of this effort is from one side to add
more attribute to Todo entity, secondarly to have a model that allow modification, it will implemented with a simple
versionable entity that let to keep track of changes and third, a more advanced search will be implemented. Search will
let to filter items in Todo list using new implemented attributes.

Modifiying an existing model and persistence entity require various step, for sake of simplicity I consider this a non
production program for which database migration are not required, hence I'll not cover in detail migration from existing
model to a new model, which likely will be a topic for a future discussion.

### New Work Item Model

this model include a more elaborate version of Todo, which will be remove and substitute by this more complete data
model.

```kotlin
package todo.domain.model

import kotlinx.serialization.Serializable

data class WorkItem(

    val id: String,

    // when this entity has been originally created and when it has been recently updated
    val created: String,
    val updated: String,

    // a number that is incremented every time this item has an atomic change
    val version: Int,

    // a reference to previous version of this entity
    val previousVersionId: String,

    // name of the person that own this item, usually is the responsible of this item but it could be another person
    val ownerId: String,
    val ownerName: String,

    // name of the person that originally created this item or the name of the person that recently changed this item
    val authorId: String,
    val authorName: String,

    // name of the person whom is responsible to accomplish this activity, it could be different from the author
    val assigneId: String,
    val assigneName: String,

    // a brief description, usually this fit a line in A4 sheet, that describe this item in simple and easy manner
    val title: String,

    // a long description, usually it include multiple things such as images, code snippet, etc
    val description: String,

    // a list of key words, either from a controlled or from a non controlled vocubolary that let users to organize items
    val tags: List<Tag>,

    // a particular status in a simple workflow that let essentially to open/close items or to defer/archive them in future
    val status: Status,

    // an estimation in terms of value either high or low for this item. Note: this is used to build a 2-d board using Haisenower matrix
    val value: Value,

    // an estimation in terms of complexity either high or low for this item. Note: this is used to build a 2-d board using Haisenower matrix
    val difficulty: Difficulty,

    // a name of the stage of the production system, tipically is "To Do", "In Progress", "Completed"
    val stage: Stage,

    // a reference of a project which contains this item. is used to group items and divide them per project
    val projectId: String,
    val projectName: String,

    // a reference of a milestone which contains this item. is used to group items and divide them per milestone
    val milestoneId: String,
    val milestoneName: String
)
```

as a conseguence of this changes, other domain model have to be implemented. Please have a look of these models here for
furthermore details.

```kotlin
package todo.domain.model

enum class Difficulty {
    TRIVIAL,
    SIMPLE,
    MODERATE,
    COMPLEX,
}
```

```kotlin
package todo.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Milestone(

    val id: String,

    val name: String,

    val created: String,

    val updated: String,

    val authorId: String,
    val authorName: String,

    val description: String,
)
```

```kotlin
package todo.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Project(

    val id: String,

    val name: String,

    val created: String,

    val updated: String,

    val authorId: String,
    val authorName: String,

    val description: String,
)
```

```kotlin
package todo.domain.model

enum class Stage {
    BACK,
    TODO,
    WORK,
    VIEW,
    TEST,
    DONE,
}
```

```kotlin
package todo.domain.model

enum class Status {
    OPEN,
    CLOSED,
    DEFERRED,
    ARCHIVED,
}
```

```kotlin
package todo.domain.model

enum class Status {
    OPEN,
    CLOSED,
    DEFERRED,
    ARCHIVED,
}
```

```kotlin
package todo.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Tag(

    val id: String,

    val name: String,

    val created: String,

    val updated: String,

    val authorId: String,
    val authorName: String,

    val description: String,
)
```

```kotlin
package todo.domain.model

enum class Value {
    HIGH,
    LOW,
}
```

### New Work Item Entity
