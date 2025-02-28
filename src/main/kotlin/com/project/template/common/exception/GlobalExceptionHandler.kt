package com.project.template.common.exception

import com.project.template.common.log.annotation.Slf4j2
import com.project.template.common.log.annotation.Slf4j2.Companion.log
import com.project.template.common.result.Result2
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@Slf4j2
@RestControllerAdvice // 拦截所有Controller，并对其产生的异常进行统一处理
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): Result2<Any> {
        log.error("{}", e.localizedMessage)
        return Result2.fail(e.message)
    }

}