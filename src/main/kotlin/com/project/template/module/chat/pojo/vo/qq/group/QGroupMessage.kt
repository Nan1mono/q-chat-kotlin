package com.project.template.module.chat.pojo.vo.qq.group

import com.google.common.collect.Lists
import com.project.template.module.chat.pojo.vo.qq.QBaseMessage
import com.project.template.module.chat.pojo.vo.qq.QMessage
import com.project.template.module.chat.pojo.vo.qq.QMessageData
import org.apache.commons.lang3.StringUtils

/**
 * QQ群组消息
 */
class QGroupMessage(
    // 群组id，即群QQ号
    var group_id: String? = null,

    // 消息体，集合形式
    var message: ArrayList<QMessage>? = arrayListOf()
) : QBaseMessage() {
    companion object {
        fun buildGroupMsg(message: String, type: String): QGroupMessage {
            // 创建主要的消息信息
            val qMessageData = QMessageData(message)
            // 构建主要消息体，也就是正确的西澳西格式
            val qMessage = QMessage(type, qMessageData)
            return QGroupMessage(message = Lists.newArrayList(qMessage))
        }

        fun buildGroupMsg(message: String, type: String, size: Int): QGroupMessage {
            if (StringUtils.isNotBlank(message) && message.length > size) {
                message.substring(0, size).plus("\r\n！由于消息限制只能显示50字符！")
            }
            return buildGroupMsg(message, type)
        }
    }

    fun toGroup(groupId: String?): QGroupMessage {
        this.group_id = groupId
        return this
    }
}