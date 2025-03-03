package com.project.template.module.chat.core.ernie

import com.project.template.common.log.annotation.Slf4j2
import com.project.template.module.chat.AiChatInterface
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Slf4j2
@Component
open class BaiduErnieService(
    @Value("\${chat.baidu.api-key:replace-your-api-key}")
    private val apiKey:String,

    @Value("\${chat.baidu.secret-key:replace-your-secret-key}")
    private val secretKey:String
):AiChatInterface {

    private val restTemplate = RestTemplate()
    override fun chat(message: String): String {
        val url = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions_pro?access_token=%s"

        return ""
    }

    override fun getAccessToken(): String {
        return ""
    }
}