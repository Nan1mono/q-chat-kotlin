package com.project.template.module.test

import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/test")
class TestController {


    @Operation(summary = "测试接口")
    @GetMapping("/hello")
    fun hello(): String {
        return "Hello World!"
    }

}