package com.project.template.module.chat.pojo.bo

import com.google.common.collect.Lists

class BaiduErnieMessageBO(

    var temperature: Double? = null,

    var top_p: Double? = null,

    var penalty_score: Double? = null,

    var enable_system_memory: Boolean? = null,

    var disable_search: Boolean? = null,

    var enable_citation: Boolean? = null,

    var messages:List<ErnieMessage>? = Lists.newArrayList()
) {
    class ErnieMessage(
        val role: String? = null,
        val content: String? = null
    )

}