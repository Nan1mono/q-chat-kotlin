package com.project.template.module.chat.pojo.vo

import com.alibaba.fastjson2.JSON

/**
 * 机器人消息基本类
 */
open class BotMessage {

    fun toJsonString(): String {
        return JSON.toJSONString(this)
    }

}