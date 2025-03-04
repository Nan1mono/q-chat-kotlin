package com.project.template.module.ha.config

import com.project.template.common.log.annotation.Slf4j2
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.config.EnableIntegration
import org.springframework.integration.websocket.ClientWebSocketContainer
import org.springframework.integration.websocket.inbound.WebSocketInboundChannelAdapter
import org.springframework.integration.websocket.outbound.WebSocketOutboundMessageHandler
import org.springframework.messaging.MessageChannel
import org.springframework.web.socket.client.WebSocketClient
import org.springframework.web.socket.client.standard.StandardWebSocketClient

@Slf4j2
@Configuration
@EnableIntegration
open class HaWebSocketConfig(
    @Value("\${home-assistant.socket-url:replace-your-url}")
    private val url: String
) {

    @Bean
    open fun webSocketClient(): WebSocketClient {
        return StandardWebSocketClient()
    }

    @Bean
    open fun outboundChannel(): MessageChannel {
        return DirectChannel()
    }

    @Bean
    open fun inboundChannel(): MessageChannel {
        return DirectChannel()
    }

    @Bean
    open fun webSocketInboundChannelAdapter(container: ClientWebSocketContainer): WebSocketInboundChannelAdapter {
        val webSocketInboundChannelAdapter = WebSocketInboundChannelAdapter(container).apply {
            outputChannel = inboundChannel()
            isAutoStartup = true
        }
        return webSocketInboundChannelAdapter
    }

    @Bean
    open fun clientWebSocketContainer(webSocketClient: WebSocketClient): ClientWebSocketContainer {
        val block: ClientWebSocketContainer.() -> Unit = {
            setConnectionTimeout(5000)
            isAutoStartup = true
        }
        val apply = ClientWebSocketContainer(webSocketClient, url).apply(block)
        apply.start()
        return apply
    }

    @Bean
    open fun webSocketOutboundMessageHandler(container: ClientWebSocketContainer): WebSocketOutboundMessageHandler {
        return WebSocketOutboundMessageHandler(container)
    }

}