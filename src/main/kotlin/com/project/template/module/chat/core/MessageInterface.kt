package com.project.template.module.chat.core

import com.project.template.module.chat.core.exception.ParsingCommandException
import com.project.template.module.chat.core.exception.enums.ParsingCommandEnum
import com.project.template.module.chat.pojo.vo.BotMessage
import java.util.regex.Pattern

interface MessageInterface {

    fun parsingCommand(message: String, regex: String): String {
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(message)
        if (!matcher.find()) {
            throw ParsingCommandException(ParsingCommandEnum.ERROR_UNKNOWN_COMMAND)
        }
        return matcher.group(1)
    }

    fun getFriendByName(message:Any): BotMessage

    fun saveFriend(message: Any): BotMessage

    fun helpSaveFriend(message: Any): BotMessage

    fun chatWithAi(message: Any): BotMessage

    fun getDeviceList(message: Any): BotMessage

    fun sendMessage(message: Any){
        // 添加发送消息的逻辑
    }

}

