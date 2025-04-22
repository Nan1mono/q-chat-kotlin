package com.project.template.module.chat.controller

import com.alibaba.fastjson2.JSONObject
import com.project.template.common.log.annotation.Slf4j2
import com.project.template.common.log.annotation.Slf4j2.Companion.log
import com.project.template.common.utils.QUtils
import com.project.template.module.chat.core.qq.impl.GroupMessageService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@Slf4j2
@RestController
@RequestMapping("/chat")
class ChatController(
    private val groupMessageService: GroupMessageService
) {
    @PostMapping("/receive")
    fun receive(@RequestBody jsonObject: JSONObject) {
        val message: String?
        try {
            message = jsonObject["raw_message"].toString()
        } catch (_: Exception) {
            return
        }
        log.info("receive message: $message")
        if (!QUtils.isGroupAt(jsonObject)) {
            return
        }
        val response = groupMessageService.chatWithAi(jsonObject)
        groupMessageService.sendMessage(response)
    }

}