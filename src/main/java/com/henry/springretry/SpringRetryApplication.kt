package com.henry.springretry

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class SpringRetryApplication

fun main(args: Array<String>) {
    SpringApplication.run(SpringRetryApplication::class.java, *args)
}
