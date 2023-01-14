plugins {
    kotlin("js") version "1.8.0"
    kotlin("plugin.serialization") version "1.8.0"
}

group = "spring-web"
version = "kotlin-1.8.0-react-18.2.0-pre.385"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react:18.2.0-pre.385")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:18.2.0-pre.385")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion:11.10.4-pre.385")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom:6.3.0-pre.385")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-redux:4.1.2-pre.385")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-redux:7.2.6-pre.385")
}

kotlin {
    js {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
    }
}
