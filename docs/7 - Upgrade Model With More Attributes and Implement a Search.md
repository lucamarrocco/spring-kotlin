## Upgrade Model With More Attributes and Implement a Search

Current Todo model is very simple and minimal, at the moment there is a simple description. Todo entity are immutable
hence there is not the possibility to update an item after is creationg. The aim of this effort is from one side to add
more attribute to Todo entity, secondarly to have a model that allow modification, it will implemented with a simple
versionable entity that let to keep track of changes and third, a more advanced search will be implemented. Search will
let to filter items in Todo list using new implemented attributes.

Modifiying an existing model and persistence entity require various step, for sake of simplicity I consider this a non
production program for which database migration are not required, hence I'll not cover in detail migration from existing
model to a new model, which likely will be a topic for a future discussion.

### Enumerated Values

**Difficulty**, it represent how simple / complex a work item is. Usually a technical person is best candidate to
evaluate this and provide to a business owner this estimation. Work item difficulty as usually effect into effort
required to complete an activity. In principle a trivial work might be completed in less time than a simple work item, a
simple work item require less effort of a moderate activity, etc. Is worth also to mention that usually moderate and /
or complex activity might be difficult to estimate and because of this potential poor effort estimation this might have
effect of risk management. A wrong estimation of an item difficulty might cause delay into work stream. Complex and
moderate item should be considered carefully, perhaps these activity should require a more advanced analysis to ensure
that the problem is well understood. Usually moderate and / or complext activity might be furthermore divided in
multiple sub-items with less difficulty and more clear scope to reduce risk associated.

**Progress**, this is an information associated into a milestone, or a sprint, but this value can be associated also
into other entities like work item or project for instance. Semantic information provide by this value is self evident,
and it could be:

- **UPCOMING** for something that is not started yet and will start in future,
- **INPROGRESS** for an activity that is started but is not finished, hence a person is working on it, or
- **COMPLETED** for something that has started, has been worked and all associated activities are now completed.
- **RELEASED** all items in that group of things has been released and is usable by end-user, is actually a production

**State**, mean various phases of a work item from its definition till the completion. These phases might depend on
various factor, and usually might be more elaborate or simple. In this example, there are 6 phases or transitions that
correspond into different columns in a kanban dashboard. These phases are:

- **BACKLOG**, an know item in a list of items,
- **TODO** when at least one of more persons commited to work on an item during a sprint, it implies that there is an
  high probability that this item will be moved towards final phase in a couple of weeks,
- **WORK** one or more person are currently working on this,
- **REVIEW** a work item has been finished and is moved into a review phase where one or more person will review that
  item,
- **TEST** a work item is moved into a test stage, where is verified that it introduce expected values but not
  unexpected surprises,
- **DONE** this usually means that an item has been completed, verified, tested, and is in line for an imminent release,
  or it has been released.

**Value**, an item value is an information that usually a business owner provide, it could be an information driven by
available or non available quantitive information and give the chance to prioritize high value item first to optimize
the amount of value provided in the unit of time.

**Status**, is an additive of an item which bring additive information, for instance:

- **OPEN** means that a more effort shuold be invested in clarify business requirements, or that not all information are
  clear and sufficient to start a working phase on this item,
- **PLANNED** it means that a work item has been suffciently described and it could be inserted in a working phase, for
  instance in a sprint or in a milestone, this usually implies that a business owner also decided to allocate working
  power and accept to invest time and money in this activity,
- **CLOSED** at the very end of a work stream, when an item has been completed and there aren't other activities
  required, or when it has been decided to not invest time and effort into an activity,
- **DEFERRED** is when an item is important long term, but a business owner has been decided that is not time now to
  invest effort in this activity, usually this is something that should be implemented but now, and for this reason it's
  moved in a future phase when a value / priority reevaluation will be executed.

```kotlin
package todo.domain.value

enum class Difficulty {
    TRIVIAL,
    SIMPLE,
    MODERATE,
    COMPLEX,
}
```

```kotlin
package todo.domain.value

enum class Progress {
    UPCOMING,
    PROGRESS,
    COMPLETE,
    RELEASED,
}
```

```kotlin
package todo.domain.value

enum class Status {
    OPEN,
    CLOSED,
    DEFERRED,
    ARCHIVED,
}
```

```kotlin
package todo.domain.value

enum class State {
    BACKLOG,
    TODO,
    WORK,
    REVIEW,
    TEST,
    DONE,
}
```

```kotlin
package todo.domain.value

enum class Status {
    OPEN,
    PLANNED,
    CLOSED,
    DEFERRED,
    ARCHIVED,
}
```

### Domain Entities

These are things that shape a tasks based application and represent various things like something to work on, a group of
things in a specific time frame, or an high level overview of all objectives and items to accomplish as a result of a
collective effort.

a **Project** has many **WorkItem**s, **Milestone**s, and **Sprint**s

a **Milestone** is composed by multiple goals that can be represented by a list of **WorkItem**s that should be
accomplished for a certain date.

a **Sprint** is a fixed amount of effort available in a specific time frame. This effort is used to accomplish
intermediate goals for a given project to progress towards a **Milestone** that bring key values to a **Project**. A **
Sprint** usually include a list of **WorkItem**s that should be completed in a time constraint envirnment, hence a list
of things that a person or a group of person commit to work on and complete in a specific period of time.

a **WorkItem** is something that specific a piece of thing that require an effort to be created. This in in practice the
core thing into this domain. **WorkItem** hold basic informations and provide insight about **What** do, **How** do it,
and **Who** will do that. There are also secondary information like **Difficulty**, **Value** that can be used to
plan **When** do a certain thing, as well ass other information like **State** and **Status** which give information
about how this item is in relation of a work stream. Other important informations are related to **Whom** is responsible
for this item result, quality and delivery and one or more persons that are responsible to work on it. Is also wort to
mention that an item might be divided into specific sub-tasks (where is possible to defined these steps) and it can
express an interdependency to other items in a work stream, for instance a **Blocker** or a **Requirement** that should
be solved or defined before this item can start.

a **Tag** is an additive information that is helpful to categorize an item using different and flexible information. As
an example of this, is possible to imagine to flag an item with a specific key word, for instance a functional component
name, a function name, a business goal, etc. In practice these additive information let to group items using different
strategy and help for instance who is responsible to group common things together to optimize value added in a time
unit, or to optimise effort grouping common things together.

Is worth to mention that to keep domain model lightweight things that are related to each other usually might include
all details, but not always. For instance references to other things might be represented with a lightweight version
that include bare minimal information, that minimise bandwith required to move information from a backend system to a
frontend system. For this reason these things are represented in this way: **MilestoneReference** provide id and title
of a milestone, **ProjectReference** provide id and title of a project, **SprintReference** provide id and title of a
sprint, **TagReference** provide id and title of a project, **WorkItemReference** provide id and title of a workitem.

```kotlin
package todo.domain.model

interface MilestoneReference {

    var id: String?

    var title: String?
}
```

```kotlin
package todo.domain.model

interface ProjectReference {

    var id: String?

    var title: String?
}
```

```kotlin
package todo.domain.model

interface SprintReference {

    var id: String?

    var title: String?
}
```

```kotlin
package todo.domain.model

interface TagReference {

    var id: String?

    var title: String?
}
```

```kotlin
package todo.domain.model

interface WorkItemReference {

    var id: String?

    var title: String?
}
```

Here a detailed list of these things definition and for each a list of attributes and relation between them.

There are also other things that are not strictly about domain, but that are in any case necessary to represent non
functional things such as Search text used to find items in this domain.

#### Project

```kotlin
package todo.domain.model

import kotlinx.serialization.Serializable

@Serializable
class Project : ProjectReference {
    override var id: String? = null

    var created: String? = null
    var createdByUserId: String? = null
    var createdByUserName: String? = null

    var updated: String? = null
    var updatedByUserId: String? = null
    var updatedByUserName: String? = null

    var authorUserId: String? = null
    var authorUserName: String? = null

    override var title: String? = null
    var description: String? = null

    var workItems: List<WorkItemReference> = mutableListOf()
}
```

#### Milestone

```kotlin
package todo.domain.model

import kotlinx.serialization.Serializable
import todo.domain.value.Status

@Serializable
class Milestone : MilestoneReference {

    override var id: String? = null

    var created: String? = null
    var createdByUserId: String? = null
    var createdByUserName: String? = null

    var updated: String? = null
    var updatedByUserId: String? = null
    var updatedByUserName: String? = null

    var authorUserId: String? = null
    var authorUserName: String? = null

    override var title: String? = null
    var description: String? = null

    var startDate: String? = null
    var endDate: String? = null

    var status: Status? = null

    var projectId: String? = null
    var projectName: String? = null

    var workItems: List<WorkItemReference> = mutableListOf()
}
```

#### Sprint

```kotlin
package todo.domain.model

import kotlinx.serialization.Serializable
import todo.domain.value.Progress

@Serializable
class Sprint : SprintReference {

    override var id: String? = null

    var created: String? = null
    var createdByUserId: String? = null
    var createdByUserName: String? = null

    var updated: String? = null
    var updatedByUserId: String? = null
    var updatedByUserName: String? = null

    var authorUserId: String? = null
    var authorUserName: String? = null

    override var title: String? = null
    var description: String? = null

    var startDate: String? = null
    var endDate: String? = null

    var status: Progress? = null

    var projectId: String? = null
    var projectName: String? = null

    var workItems: List<WorkItemReference> = mutableListOf()
}
```

#### WorkItem

```kotlin
package todo.domain.model

import kotlinx.serialization.Serializable

import todo.domain.value.Difficulty
import todo.domain.value.State
import todo.domain.value.Status
import todo.domain.value.Value

@Serializable
class WorkItem : WorkItemReference {

    override var id: String? = null

    // an incremental number that identify this work item within a project
    var number: Int? = null
  
    // when this entity has been originally created
    var created: String? = null

    // reference to the user that changed this work item recently
    var createdByUserId: String? = null
    var createdByUserName: String? = null

    // when this entity has been recently updated
    var updated: String? = null

    // reference to the user that changed this work item recently
    var updatedByUserId: String? = null
    var updatedByUserName: String? = null

    // a number that is incremented every time this item has an atomic change
    var version: Int? = null

    // a reference to previous version of this entity
    var previousVersionId: String? = null

    // name of the person that own this item, usually is the responsible of this item but it could be another person
    var ownerUserId: String? = null
    var ownerUserName: String? = null

    // name of the person that originally created this item or the name of the person that recently changed this item
    var authorUserId: String? = null
    var authorUserName: String? = null

    // name of the person whom is responsible to accomplish this activity, it could be different from the author
    var assigneeId: String? = null
    var assigneeName: String? = null

    // a brief description, usually this fit a line in A4 sheet, that describe this item in simple and easy manner
    override var title: String? = null

    // a long description, usually it include multiple things such as images, code snippet, etc
    var description: String? = null

    // a list of key words, either from a controlled or from a non controlled vocubolary that let users to organize items
    var tags: List<Tag> = listOf()

    // a name of the stage of the production system, tipically is "To Do", "In Progress", "Completed"
    var state: State? = State.BACKLOG

    // a particular status in a simple workflow that let essentially to open/close items or to defer/archive them in future
    var status: Status? = null

    // an estimation in terms of value either high or low for this item. Note: this is used to build a 2-d board using Haisenower matrix
    var value: Value? = null

    // an estimation in terms of complexity either high or low for this item. Note: this is used to build a 2-d board using Haisenower matrix
    var difficulty: Difficulty? = null

    // a reference of a project which contains this item. is used to group items and divide them per project
    var projectId: String? = null
    var projectName: String? = null

    // a reference of a sprint which contains this item. is used to group items and divide them per sprint
    var sprintId: String? = null
    var sprintName: String? = null

    // a reference of a milestone which contains this item. is used to group items and divide them per milestone
    var milestoneId: String? = null
    var milestoneName: String? = null
}
```

#### Tag

```kotlin

package todo.domain.model

import kotlinx.serialization.Serializable

@Serializable
class Tag : TagReference {

    override var id: String? = null

    var created: String? = null
    var createdByUserId: String? = null
    var createdByUserName: String? = null

    var updated: String? = null
    var updatedByUserId: String? = null
    var updatedByUserName: String? = null

    var authorUserId: String? = null
    var authorUserName: String? = null

    override var title: String? = null
    var description: String? = null
}
```

a **SearchInput** is a thing that represent an user input, eg **text** used to find a particular thing in this domain.

```kotlin
package todo.domain.model

import kotlinx.serialization.Serializable

@Serializable
class SearchInput {
    var text: String? = null
}
```
