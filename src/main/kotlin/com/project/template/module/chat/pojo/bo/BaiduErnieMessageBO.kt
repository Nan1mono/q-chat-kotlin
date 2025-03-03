package com.project.template.module.chat.pojo.bo

class BaiduErnieMessageBO(

    var temperature: Double? = null,

    var top_p: Double? = null,

    var penalty_score: Double? = null,

    var enable_system_memory: Boolean? = null,

    var disable_search: Boolean? = null,

    var enable_citation: Boolean? = null
) {
    class ErnieMessage(
        var role: String? = null,
        var content: String? = null
    )

}