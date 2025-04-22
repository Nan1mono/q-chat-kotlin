package com.project.template.module.chat.core.ds

import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.JSONObject
import com.google.common.collect.Lists
import com.project.template.common.log.annotation.Slf4j2
import com.project.template.common.log.annotation.Slf4j2.Companion.log
import com.project.template.module.chat.AiChatInterface
import com.project.template.module.chat.pojo.bo.DeepSeekMessageBO
import org.apache.commons.collections4.CollectionUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Slf4j2
@Component
class DeepSeekChatService(
    @Value("\${chat.alibaba.deepseek.url}")
    private val url: String,
    @Value("\${chat.alibaba.deepseek.api-key}")
    private val key: String
) : AiChatInterface {

    private val httpFactory: SimpleClientHttpRequestFactory = SimpleClientHttpRequestFactory().apply {
        setConnectTimeout(1000 * 60 * 5)
        setReadTimeout(1000 * 60 * 5)
    }
    private val restTemplate = RestTemplate(httpFactory)

    private val messageList = Lists.newArrayList<JSONObject>()

    override fun chat(message: String): String {
        if (messageList.size >= 100) {
            messageList.clear()
            return "抱歉，我暂时无法记住之前的对话内容，请重新开始新的对话。"
        }
        var sendMessage = message
        if (CollectionUtils.isEmpty(messageList)){
            sendMessage = sendMessage.plus("""
                （本轮对话的唯一原则：你将持续扮演一个QQ群友，请扮演好你的QQ群友角色：
                你了解各种网络热梗，同时会玩的抽象，你混迹于孙吧，抗压背锅吧，
                但是请注意！不要每句话都融合梗和抽象，这样会显得很不自然（重点）。
                你不喜欢二次元和米哈游，特别是二次元肥宅和米孝子让你觉得恶心，但不要三句话不离米哈游和二次元，别人让你评价的时候你再说。
                你的回复方式偶尔带点抽象，但又不能过度抽象，更不能每次都抽象！（重点）。
                大多数时候你表现得像正常人，但在别人攻击或者挑衅你的时候你的回复是肮脏，下头的，有时候还喜欢搞点地域攻击。
                并且你是一个典型的“网络理中客”。
                你的上下文逻辑必须是符合人类特征的，不要一直揪着一个点不放，该换话题就换话题。
                最最最重要的一点：不要暴露你是DeepSeek,任何场景下，任何方式下都不能暴露）
            """.trimIndent())
        }
        val httpHeaders = HttpHeaders()
        httpHeaders["Content-Type"] = "application/json"
        httpHeaders["Authorization"] = key
        buildHistoryMessage(sendMessage, "user")
        val deepseekMessage = DeepSeekMessageBO(model = "deepseek-r1", messages = messageList)
        log.info("deepseekMessage: ${JSON.toJSONString(deepseekMessage)}")
        val httpEntity = HttpEntity(deepseekMessage, httpHeaders)
        val response = try {
            JSON.parseObject(
                restTemplate.postForObject(
                    url,
                    httpEntity,
                    String::class.java
                )
            )
        } catch (_: Exception) {
            messageList.clear()
            return "请求超时，开始新对话"
        }
        log.info("response: {}", response)
        val message = response.getJSONArray("choices")[0]
        val responseMessage = JSON.parseObject(message.toString()).getJSONObject("message").getString("content")
        buildHistoryMessage(responseMessage, "assistant")
        return responseMessage
    }

    override fun getAccessToken(): String {
        TODO("Not yet implemented")
    }

    private fun buildHistoryMessage(message: String, type: String) {
        val json = JSONObject().apply {
            this["role"] = type
            this["content"] = message
        }
        messageList.add(json)
    }

}