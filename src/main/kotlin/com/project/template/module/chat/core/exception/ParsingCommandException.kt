package com.project.template.module.chat.core.exception

import com.project.template.module.chat.core.exception.enums.ParsingCommandEnum

class ParsingCommandException(val code: Int, message: String? = null) : RuntimeException(message) {
    constructor(enum: ParsingCommandEnum) : this(enum.codeNo, String.format("%s-%s", enum.code, enum.message))
}