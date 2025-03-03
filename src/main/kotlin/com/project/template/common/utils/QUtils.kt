package com.project.template.common.utils

import com.alibaba.fastjson2.JSONObject

sealed class QUtils {

    companion object {
        const val RAW_MESSAGE = "raw_message"

        const val SELF_ID = "self_id"

        const val GROUP_ID = "group_id"

        const val AT_MESSAGE = "[CQ:at,qq=%s]"

        fun getGroupId(jsonObject: JSONObject): String {
            return jsonObject.getString(GROUP_ID)
        }

        fun getQid(jsonObject: JSONObject): String {
            return jsonObject.getString(SELF_ID)
        }

        fun isGroup(jsonObject: JSONObject): Boolean {
            val str: String = try {
                jsonObject.getString(RAW_MESSAGE)
            } catch (e: Exception) {
                ""
            }
            return str.startsWith(String.format(AT_MESSAGE, getQid(jsonObject)))
        }
    }

}