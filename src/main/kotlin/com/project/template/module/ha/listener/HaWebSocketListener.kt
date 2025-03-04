package com.project.template.module.ha.listener

import com.alibaba.fastjson2.JSON
import com.project.template.common.log.annotation.Slf4j2
import com.project.template.common.log.annotation.Slf4j2.Companion.log
import com.project.template.module.chat.core.qq.impl.GroupMessageService
import com.project.template.module.chat.core.tg.InitializationTgBot
import com.project.template.module.chat.pojo.vo.qq.group.QGroupMessage
import com.project.template.module.chat.service.HomeAssistantHandshakeService
import com.project.template.module.chat.service.HomeAssistantUserService
import com.project.template.module.ha.HaSocketTemplate
import org.springframework.context.annotation.Configuration
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.messaging.Message

@Slf4j2
@Configuration
open class HaWebSocketListener(
    private val homeAssistantHandshakeService: HomeAssistantHandshakeService,
    private val homeAssistantUserService: HomeAssistantUserService,
    private val haSocketTemplate: HaSocketTemplate,
    private val initializationTgBot: InitializationTgBot,
    private val groupMessageService: GroupMessageService
) {

    @ServiceActivator(inputChannel = "inboundChannel")
    fun listen(message: Message<String>) {
        val parseObject = JSON.parseObject(message.payload)
        val type = parseObject.getString("type")
        val success = parseObject.getString("success")
        when {
            type == "auth_required" || type == "auth_ok" -> return
            success == "false" || success != "true" -> return
        }
        val payloadId = parseObject.getString("id")
        homeAssistantHandshakeService.getByPayloadId(payloadId).let { shake ->
            if (shake == null) {
                log.error("shake is null")
                return
            }
            homeAssistantUserService.getById(shake.homeAssistantUserId).let { user ->
                if (user == null) {
                    return
                }
                when (shake.sessionMethod) {
                    "telegram" ->
                        haSocketTemplate.filterDeviceListFromTelegram(message.payload, user.telegramId).run {
                            initializationTgBot.nanimonoBot.sendMessageToChat(this, shake.chatId)
                        }

                    "qq" ->
                        haSocketTemplate.filterDeviceListFromQid(message.payload, user.qqId).run {
                            groupMessageService.sendMessage(
                                QGroupMessage.buildGroupMsg(this, "text").toGroup(shake.chatId)
                            )
                        }
                }
            }
        }

    }

}