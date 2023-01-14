## Setting up simple Spring Boot web application

### Spring Boot Application

create a file named ```App.kt``` into ```src/main/kotlin/spring/kotlin/web```

```kotlin
package todo.web

import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class App
```

### Spring Boot Application Run

add main function using SprintApplication object to run defined Spring Boot application ```App```

```kotlin
package todo.web

import org.springframework.boot.SpringApplication

fun main() {
    SpringApplication.run(App::class.java)
}
```

## Spring Boot Rest Controller

create a file named ```AppController.kt``` in ```src/main/kotlin/base```

annotation ```AppController``` class using ```RestController``` annotation

```kotlin
package todo.web

import org.springframework.web.bind.annotation.RestController

@RestController
class AppController
```

add a method handler (function) named ```hello``` and annotate this method with ```GetMapping("/hello")``` annotation.
The effect of this method declaration will be to expose an http handler that will return string ```Hello World!``` for
http request ```curl -X GET http://localhost:8080/hello```

```kotlin
package todo.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AppController {
    @GetMapping("/hello")
    fun hello() = "Hello World!"
}
```

add a request parameter ```name: String``` and annotate this parameter with ```RequestParam("name")``` annotation. As
effect of this change a query paramter named ```name``` will be required to invoke this handler, hence http request to
call this method will be ```curl -X GET http://localhost:8080/hello?name=World```

```kotlin
package todo.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AppController {
    @GetMapping("/hello")
    fun hello(@RequestParam("name") name: String) = "Hello $name!"
}
```

## Gradle Build

crate a file named ```build.gradle.kts```

### Gradle Kotlin Plugins

declare kotlin plugins version 1.7.22

```kotlin
plugins {
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
}
```

### Gradle Spring Plugins

declare spring plugins version 3.0.1

```kotlin
plugins {
    id("org.springframework.boot") version "3.0.1"
    id("io.spring.dependency-management") version "1.1.0"
}
```

### Gradle Artifact Group and Version

define project group and version. I use ```spring-3.0.1-kotlin-1.7.22-SNAPSHOT``` to keep track spring and kotlin
version used in this project example. You can define whatever version you wish, eg ```1.0-SNAPSHOT```

```kotlin
group = "spring-kotlin-web"
version = "spring-3.0.1-kotlin-1.7.22-SNAPSHOT"
```

### Java Version 17 Requirements

Spring Boot 3.0.1 require Java version 17

```kotlin
java.sourceCompatibility = JavaVersion.VERSION_17
```

```kotlin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}
```

### Repositories

Nothing special here, just maven central repository should be sufficient to retrieve all necessary dependencies.

```kotlin
repositories {
    mavenCentral()
}
```

### Kotlin Dependencies

Kotlin dependencies are required for all kotlin projects builtd using gradle.

```kotlin
dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}
```

### Spring Dependencies

Spring dependencies specific for this project example.

```kotlin
dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
}
```

### Spring Test Dependencies

Spring dependencies specific to test this project example.

```kotlin
dependencies {
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
```

### Test using JUnit platform

Configure JUnit platform to run tests

```kotlin
tasks.withType<Test> {
    useJUnitPlatform()
}
```
