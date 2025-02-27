package com.project.template.common.exception

import com.project.template.common.result.Result2
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice // 拦截所有Controller，并对其产生的异常进行统一处理
class GlobalExceptionHandler {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): Result2<Any> {
        log.error("{}", e.localizedMessage)
        return Result2.fail(e.message)
    }

}