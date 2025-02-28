package com.project.template

import com.project.template.common.log.annotation.Slf4j2
import com.project.template.common.log.annotation.Slf4j2.Companion.log
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@Slf4j2
@SpringBootApplication
open class ApiApplication {

    fun start(args: Array<String>) {
        SpringApplication.run(ApiApplication::class.java, *args)
        log.info("ApiApplication start success!")
        log.info("swagger doc.html url:{}", "http://localhost:38547/v3/api-docs")
    }
}

fun main(args: Array<String>) {
    ApiApplication().start(args)
}