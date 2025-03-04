package com.project.template.module.chat.core.tg

import com.project.template.module.chat.core.ernie.BaiduErnieService
import com.project.template.module.chat.core.exception.ChatCoreException
import com.project.template.module.chat.core.tg.bot.NanimonoBot
import com.project.template.module.chat.service.HomeAssistantUserService
import com.project.template.module.ha.HaSocketTemplate
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@Component
open class InitializationTgBot(
    private val baiduErnieService: BaiduErnieService,
    private val homeAssistantUserService: HomeAssistantUserService,
    private val haSocketTemplate: HaSocketTemplate,

    @Value("\${chat.telegram.bot-token:replace-your-bot-token}")
    private val botToken: String,

    @Value("\${chat.telegram.bot-name:replace-your-bot-name}")
    private val botName: String,
) {

    lateinit var nanimonoBot: NanimonoBot

    @PostConstruct
    fun init() {
        try {
            DefaultBotOptions().apply {
                proxyHost = "127.0.0.1"
                proxyPort = 7890
            }.also { options ->
                nanimonoBot = NanimonoBot(
                    options = options, botToken = botToken, botName = botName,
                    baiduErnieService = baiduErnieService, homeAssistantUserService = homeAssistantUserService,
                    haSocketTemplate = haSocketTemplate
                )
                TelegramBotsApi(DefaultBotSession::class.java).registerBot(nanimonoBot)
            }
        } catch (e: TelegramApiException) {
            throw ChatCoreException(e)
        }
    }
}