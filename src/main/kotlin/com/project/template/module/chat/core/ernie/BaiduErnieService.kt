package com.project.template.module.chat.core.ernie

import com.alibaba.fastjson2.JSON
import com.google.common.collect.Lists
import com.project.template.common.log.annotation.Slf4j2
import com.project.template.module.chat.AiChatInterface
import com.project.template.module.chat.pojo.bo.BaiduErnieMessageBO
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Slf4j2
@Component
open class BaiduErnieService(
    @Value("\${chat.baidu.api-key:replace-your-api-key}")
    private val apiKey: String,

    @Value("\${chat.baidu.secret-key:replace-your-secret-key}")
    private val secretKey: String
) : AiChatInterface {

    private val restTemplate = RestTemplate()

    override fun chat(message: String): String {
        val url = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions_pro?access_token=%s"
        val ernieMessage = BaiduErnieMessageBO().apply {
            messages = Lists.newArrayList(BaiduErnieMessageBO.ErnieMessage("user", message))
            temperature = 0.95
            top_p = 0.8
            penalty_score = 1.0
            enable_system_memory = false
            disable_search = false
            enable_citation = false
        }
        val accessToken = getAccessToken()
        val httpHeaders = HttpHeaders()
        httpHeaders["Content-Type"] = "application/json"
        val httpEntity = HttpEntity(ernieMessage, httpHeaders)
        val response = JSON.parseObject(
            restTemplate.postForObject(
                String.format(url, accessToken),
                httpEntity,
                String::class.java
            )
        )
        return response.getString("result");

    }

    override fun getAccessToken(): String {
        val httpHeaders = HttpHeaders()
        httpHeaders["Content-Type"] = "application/json"
        val url = String.format(
            "https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=%s&client_secret=%s",
            apiKey,
            secretKey
        )
        val httpEntity = HttpEntity<String>(httpHeaders)
        val response = JSON.parseObject(restTemplate.postForObject(url, httpEntity, String::class.java))
        return response.getString("access_token")
    }
}