package com.project.template.module.chat.core.exception.enums

enum class ParsingCommandEnum(
    val codeNo: Int,
    val code: String,
    val message: String
) {
    SUCCESS(1000, "PCE-1000", "成功"),
    ERROR(2000, "PCE-2000", "失败"),
    ERROR_UNKNOWN_COMMAND(2001, "PCE-2001", "未知命令"),
}