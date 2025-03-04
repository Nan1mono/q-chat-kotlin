package com.project.template.module.ha

import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.JSONObject
import com.project.template.common.log.annotation.Slf4j2
import com.project.template.module.chat.core.exception.ChatCoreException
import com.project.template.module.chat.core.exception.enums.ChatEnum
import com.project.template.module.chat.service.HomeAssistantUserService
import com.project.template.module.ha.sender.HaWebSocketSender
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Slf4j2
@Component
open class HaSocketTemplate(
    @Value("\${home-assistant.token}")
    private val token: String,

    private val haWebSocketSender: HaWebSocketSender,
    private val homeAssistantUserService: HomeAssistantUserService
) {

    companion object {
        private const val UNBOUND = "该用户尚未绑定HomeAssistant"

        protected fun getDeviceList(): JSONObject {
            val jsonObject = JSONObject()
            jsonObject["id"] = System.currentTimeMillis()
            jsonObject["type"] = "config/device_registry/list"
            return jsonObject
        }
    }

    fun searchDevice(sessionMethod: String, homeAssistantUserId: Long?, chatId: String) {
        if (homeAssistantUserId == null) {
            throw ChatCoreException(ChatEnum.MISS_PARAM_ERROR)
        }
        haWebSocketSender.sendToWebSocket(
            JSON.toJSONString(getDeviceList()),
            sessionMethod,
            homeAssistantUserId,
            chatId
        )
    }

    fun filterDeviceListFromTelegram(message: String, telegramId: Long?): String {
        if (telegramId == null){
            return UNBOUND
        }
        homeAssistantUserService.getByTelegramId(telegramId).let {
            return if (it != null) {
                this.filterDeviceList(message, it.primaryConfigEntry)
            } else {
                UNBOUND
            }
        }
    }

    fun filterDeviceListFromQid(message: String, qid: Long?): String {
        if (qid == null){
            return UNBOUND
        }
        homeAssistantUserService.getByQid(qid).let {
            return if (it != null) {
                this.filterDeviceList(message, it.primaryConfigEntry)
            } else {
                UNBOUND
            }
        }
    }

    private fun filterDeviceList(message: String, primaryConfigEntry: String?): String {
        if (StringUtils.isBlank(primaryConfigEntry)) {
            return "未配置primaryConfigEntry"
        }
        val deviceNameList =
            JSON.parseObject(message).getJSONArray("result").stream().map { JSON.parseObject(it.toString()) }
                .filter { it.getString("primary_config_entry") == primaryConfigEntry }.map { it.getString("name") }
                .toList()
        return if (CollectionUtils.isEmpty(deviceNameList)) {
            "名下没有任何设备"
        } else {
            String.format(
                """
                🤖目前您的智能资产如下：
                
                %s
            """.trimIndent(), deviceNameList.joinToString("\r\n")
            )
        }
    }
}