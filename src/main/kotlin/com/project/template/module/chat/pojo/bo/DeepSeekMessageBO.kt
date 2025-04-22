package com.project.template.module.chat.pojo.bo

import com.alibaba.fastjson2.JSONObject

class DeepSeekMessageBO(
    val model: String = "deepseek-v3",
    val messages:List<JSONObject>
)