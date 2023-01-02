package spring.kotlin

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.transaction.annotation.EnableTransactionManagement
import spring.kotlin.hibernate.entity.TodoEntity

@SpringBootApplication
class App

fun main() {
    SpringApplication.run(App::class.java)
}

