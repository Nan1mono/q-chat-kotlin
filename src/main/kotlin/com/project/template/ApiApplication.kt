package com.project.template

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class ApiApplication {
    private val log: Logger = LoggerFactory.getLogger(ApiApplication::class.java)

    fun start(args: Array<String>) {
        SpringApplication.run(ApiApplication::class.java, *args)
        log.info("ApiApplication start success!")
        log.info("swagger doc.html url:{}", "http://localhost:38547/v3/api-docs")
    }
}

fun main(args: Array<String>) {
    ApiApplication().start(args)
}