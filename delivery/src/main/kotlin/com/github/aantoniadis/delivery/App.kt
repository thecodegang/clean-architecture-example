package com.github.aantoniadis.delivery

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = [
    "com.github.aantoniadis.delivery.config",
    "com.github.aantoniadis.dataproviders.db.mongo.config",
    "com.github.aantoniadis.delivery.rest.imp"
])
class App

fun main(args: Array<String>) {
    SpringApplication.run(App::class.java, *args)
}
