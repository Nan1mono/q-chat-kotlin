package com.project.template.module.chat.core.tg.bot

import com.project.template.common.log.annotation.Slf4j2
import com.project.template.module.chat.core.ernie.BaiduErnieService
import com.project.template.module.chat.core.exception.ChatCoreException
import com.project.template.module.chat.service.HomeAssistantUserService
import com.project.template.module.ha.HaSocketTemplate
import org.apache.commons.lang3.StringUtils
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

@Slf4j2
class NanimonoBot(
    options: DefaultBotOptions,
    botToken: String,
    private val botName: String,
    private val baiduErnieService: BaiduErnieService,
    private val haSocketTemplate: HaSocketTemplate,
    private val homeAssistantUserService: HomeAssistantUserService
) : TelegramLongPollingBot(options, botToken) {

    override fun getBotUsername(): String {
        return this.botName
    }

    override fun onUpdateReceived(update: Update?) {
        if (update != null && update.hasMessage() && update.message.hasText()) {
            val chatId = update.message.chatId
            val fromUserId = update.message.from.id
            // 查询是否配置了用户
            val homeAssistantUser = homeAssistantUserService.getByTelegramId(fromUserId)
            val text = if (homeAssistantUser != null && "设备列表" == update.message.text) {
                haSocketTemplate.searchDevice("telegram", homeAssistantUser.id, chatId.toString())
                ""
            } else {
                baiduErnieService.chat(update.message.text)
            }
            if (StringUtils.isNotBlank(text)) {
                SendMessage().apply {
                    this.text = text
                    this.chatId = chatId.toString()
                }.also {
                    try {
                        execute(it)
                    } catch (e: TelegramApiException) {
                        throw ChatCoreException(e)
                    }
                }
            }
        }
    }

    fun sendMessageToChat(message: String?, chatId: String?) {
        if (message == null || chatId == null || StringUtils.isBlank(message) || StringUtils.isBlank(chatId)) {
            return
        }
        SendMessage().apply {
            this.chatId = chatId
            this.text = message
        }.run {
            execute(this)
        }
    }
}