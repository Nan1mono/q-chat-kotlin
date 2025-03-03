package com.project.template.module.chat.core.qq.impl

import com.alibaba.fastjson2.JSON
import com.google.common.collect.Maps
import com.project.template.common.utils.QUtils
import com.project.template.module.chat.core.qq.QMessageInterface
import com.project.template.module.chat.entity.Friend
import com.project.template.module.chat.pojo.vo.BotMessage
import com.project.template.module.chat.pojo.vo.qq.group.QGroupMessage
import com.project.template.module.chat.service.FriendService
import com.project.template.module.chat.service.HomeAssistantUserService
import org.apache.commons.lang3.ObjectUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate

@Component
open class GroupMessageService(
    private val friendService: FriendService,
    private val homeAssistantUserService: HomeAssistantUserService,

    @Value("\${chat.napcat.client.group-url}")
    private val clientGroupUrl:String
) : QMessageInterface {

    private val restTemplate: RestTemplate = RestTemplate()

    override fun sendMessage(message: Any) {
        if (ObjectUtils.isNotEmpty(message)){
            val headers=HttpHeaders()
            headers["Content-Type"] = "application/json"
            val httpEntity = HttpEntity(message, headers)
            restTemplate.postForObject(clientGroupUrl, httpEntity, String::class.java)
        }
    }

    override fun getFriendByName(message: Any): BotMessage {
        val parseObject = JSON.parseObject(message.toString())
        val rawMessage = parseObject.getString(QUtils.RAW_MESSAGE)
        val regex = "介绍-(.*)"
        val friendList = friendService.getFriendByName(parsingCommand(rawMessage, regex))
        return QGroupMessage.buildGroupMsg(Friend.convert2Msg(friendList), "text").toGroup(QUtils.getGroupId(parseObject))
    }

    override fun saveFriend(message: Any): BotMessage {
        TODO("Not yet implemented")
    }

    override fun helpSaveFriend(message: Any): BotMessage {
        TODO("Not yet implemented")
    }

    override fun chatWithAi(message: Any): BotMessage {
        TODO("Not yet implemented")
    }

    override fun getDeviceList(message: Any): BotMessage {
        TODO("Not yet implemented")
    }


}