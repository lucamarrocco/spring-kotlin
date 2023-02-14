plugins {
    kotlin("js") version "1.8.10"
    kotlin("plugin.serialization") version "1.8.0"
}

group = "spring-kotlin"
version = "kotlin-1.8.0-react-18.2.0-pre.385"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":todo-model"))

    implementation("org.jetbrains.kotlin-wrappers:kotlin-react:18.2.0-pre.385")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:18.2.0-pre.385")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion:11.10.4-pre.385")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom:6.3.0-pre.385")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-redux:4.1.2-pre.385")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-redux:7.2.6-pre.385")

    implementation(npm("axios", "1.3.2"))

    testImplementation(kotlin("test"))
}

kotlin {
    js {
        binaries.executable()

        browser {
            runTask {
                devServer = devServer?.copy(port = 3000)
            }

            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
    }
}
