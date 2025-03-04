package com.project.template.module.chat.core.exception

import com.project.template.common.result.ResultCodeEnum
import com.project.template.module.chat.core.exception.enums.ChatEnum

class ChatCoreException(val code: Int, message: String? = null, e: Exception?) : RuntimeException(message, e) {

    constructor(enum: ChatEnum) : this(enum.codeNo, String.format("%s-%s", enum.code, enum.message), null)

    constructor(e: Exception) : this(ResultCodeEnum.UNKNOWN_ERROR.code, e.message, e)

}