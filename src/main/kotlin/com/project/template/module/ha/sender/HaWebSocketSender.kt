package com.project.template.module.ha.sender

import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.JSONObject
import com.project.template.common.log.annotation.Slf4j2
import com.project.template.module.chat.entity.HomeAssistantHandshake
import com.project.template.module.chat.service.HomeAssistantHandshakeService
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.support.MessageBuilder
import org.springframework.integration.websocket.outbound.WebSocketOutboundMessageHandler
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Slf4j2
@Component
class HaWebSocketSender(
    @Value("\${home-assistant.token}")
    private val token: String,
    private val messageHandler: WebSocketOutboundMessageHandler,
    private val homeAssistantHandshakeService: HomeAssistantHandshakeService,
) {

    @ServiceActivator(inputChannel = "webSocketOutboundChannel")
    fun sendToWebSocket(
        @Payload payload: String,
        @Header(name = "sessionMethod", required = false) sessionMethod: String?,
        @Header(name = "homeAssistantUserId", required = false) homeAssistantUserId: Long?,
        @Header(name = "chatId", required = false) chatId: String?
    ) {
        val `object` = JSON.parseObject(payload)
        val message = MessageBuilder.withPayload(payload)
            .build()
        val apply: HomeAssistantHandshake.() -> Unit = {
            this.chatId = chatId
            this.payloadId = `object`.getString("id")
            this.sessionMethod = sessionMethod
            this.homeAssistantUserId = homeAssistantUserId
        }
        val handshake: HomeAssistantHandshake = HomeAssistantHandshake().apply(apply)
        messageHandler.handleMessage(message)
        homeAssistantHandshakeService.save(handshake)
    }


    @PostConstruct
    fun auth() {
        messageHandler.handleMessage(MessageBuilder.withPayload(sendAuthMessage().toJSONString()).build())
    }

    private fun sendAuthMessage(): JSONObject {
        val jsonObject = JSONObject()
        jsonObject["type"] = "auth"
        jsonObject["access_token"] = token
        return jsonObject
    }


}