package com.project.template.module.chat.core.qq.impl

import com.alibaba.fastjson2.JSON
import com.project.template.common.utils.QUtils
import com.project.template.module.chat.core.qq.QMessageInterface
import com.project.template.module.chat.entity.Friend
import com.project.template.module.chat.pojo.vo.BotMessage
import com.project.template.module.chat.pojo.vo.qq.group.QGroupMessage
import com.project.template.module.chat.service.FriendService
import com.project.template.module.chat.service.HomeAssistantUserService
import org.apache.commons.lang3.ObjectUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
open class GroupMessageService(
    private val friendService: FriendService,
    private val homeAssistantUserService: HomeAssistantUserService,

    @Value("\${chat.napcat.client.group-url}")
    private val clientGroupUrl: String
) : QMessageInterface {

    private val restTemplate: RestTemplate = RestTemplate()

    override fun sendMessage(message: Any) {
        if (ObjectUtils.isNotEmpty(message)) {
            val headers = HttpHeaders()
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
        return QGroupMessage.buildGroupMsg(Friend.convert2Msg(friendList), "text")
            .toGroup(QUtils.getGroupId(parseObject))
    }

    override fun saveFriend(message: Any): BotMessage {
        val regex = "添加-(.*)"
        val parseObject = JSON.parseObject(message.toString())
        val split = parsingCommand(parseObject.getString(QUtils.RAW_MESSAGE), regex).split(",")
        return if (friendService.save(split[0], split[1], split[2], split[3], split[4])) {
            QGroupMessage.buildGroupMsg("添加成功", "text").toGroup(QUtils.getGroupId(parseObject))
        } else {
            QGroupMessage.buildGroupMsg("添加失败", "text").toGroup(QUtils.getGroupId(parseObject))
        }
    }


    override fun helpSaveFriend(message: Any): BotMessage {
        return QGroupMessage.buildGroupMsg(
                """
                🫡
                按照如下格式输入：
                添加-简称,姓名,昵称1,昵称2,昵称3
                即可添加信息捏
                ❤️
                """.trimIndent(), "text").toGroup(QUtils.getGroupId(JSON.parseObject(message.toString())))
    }

    override fun chatWithAi(message: Any): BotMessage {
        TODO("Not yet implemented")
    }

    override fun getDeviceList(message: Any): BotMessage {
        TODO("Not yet implemented")
    }


}