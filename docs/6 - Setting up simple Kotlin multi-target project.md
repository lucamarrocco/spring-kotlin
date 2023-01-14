## Setting up simple Kotlin multi-target project

The reason why is helpful to split a library in three different parts is because to have the ability to share code
between multiple project side, to avoid duplications either semantic or syntactic, minimise code, better use abstraction
and promote reusability into a multi project / product program. Please note that Kotlin has very high level of support
for Native compilation which is the ability to compile front program into native target which give the ability to
optimise compiled code to run using native runtime, ie not using JVM. This solution has its own reasons, advantages and
disavantages but because of the complexity of this solution is not yet added to this documentation. There will be
another article where I speak specifically about native compilation, perhaps with a native mobile example (either
Android or Ios) and / or a native program to run a microservice in a cloud environment. But for now, the scope of this
document is to create a more common project structure that include the presence of a multi target library as described
briefly above.

### Create a Common Library

this library will include agnostic browser / backend code, that's is code written in a way to be independent from target
environment, hence code that can run either in a java / jvm environment or compiled in javascript into a browser /
javascript native environment.

### Cleanup and Refactoring current project configuration

First thing first, before going into more details, we should change a bit somethings here and there from current project
layout and configuation. This is because using a shared library implies both gradle changes but also other code changes
in dependant repositories in a way to leverage new shared / common code across different layers / targets.

first modify root ```build.gradle.kts``` to this content

```kotlin
plugins {
    kotlin("js") version "1.8.0" apply false
    kotlin("jvm") version "1.8.0" apply false
    kotlin("kapt") version "1.8.0" apply false
    kotlin("multiplatform") version "1.8.0" apply false
    kotlin("plugin.spring") version "1.8.0" apply false
}
```

this is because gradle, especially with multi module projects, require to declare all plugins in the root configuration,
please not the ```apply false``` instruction, in a way to have a common place where all plugins are declared and
configured.

second create a module with this folder structure

```shell
library
`- src
  `- commonMain
    `- kotlin
```

in ```library``` directory create a file named ```build.gradle.kts```

```kotlin
plugins {
    kotlin("multiplatform") version "1.8.0"
    kotlin("plugin.serialization") version "1.8.0"
}

repositories {
    mavenCentral()
}

kotlin {
    js("browser", BOTH) {
        nodejs()
        browser()
        useCommonJs()
    }

    jvm("backend") {}

    sourceSets {
        commonMain {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
            }
        }
    }
}
```

and create a file named ```Todo.kt``` in ```todo.domain.model``` package

```kotlin
package todo.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Todo(

    val id: String,

    val created: String,

    val description: String,
)
```

as effect of above mentioned changes, the library project is now configured to build multi target artifacts with a
single class / object which is agnostic to the target environment. A model object like in this case is a good candidate
for this exercise because different reasons. First this data model which represent a Todo item, is used from both client
and server implementation, from the client to send or received a todo or a list of todo, and from the server in the
opposite direction to receive or return a new todo or a list of todo. By acting this change, moving Todo declaration
from both ```kotlin-react``` and ```kotlin-spring``` into this common library with obtain the immediate effect to have a
shared model between client and server with immediate benefits in terms of code deduplication, improved reusability and
more important make client and server protocol implementation perfectly simmetrical.

Please not that previous Todo declaration from dependant repository are not require any more and for this reason we
should remove ```Todo.ks``` from ```domain.model``` in both ```todo-react``` and ```todo-spring``` modules.

As you might notice by removing these classes is a necessary but not sufficient step, to complete this code refactoring
one more step is required which is make ```library``` module a dependency of both ```todo-react``` and ```todo-spring```
modules.

There are three very simple steps for this:

1. add ```library``` in gradle multi module setup. Change root ```setting.gradle.kts``` in this way:
```kotlin
include("library")
include("todo-react")
include("todo-spring")
```
as effect of this, gradle will include ```library``` module in build

2. add ```library``` in other required ```build.gradle.kts```:

todo-react/build.gradle.kts

```kotlin
dependencies {
    implementation(project(":library"))
}
```

and in todo-spring/build.gradle.kts

```kotlin
dependencies {
    implementation(project(":library"))
}
```
