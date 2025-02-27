package com.project.template.module.chat.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.project.template.module.base.entity.BaseEntity;

/**
 * <p>
 * 
 * </p>
 *
 * @author lee
 * @since 2025-02-27
 */
@TableName("home_assistant_handshake")
class HomeAssistantHandshake : BaseEntity() {

    /**
     * 会话id
     */
    @TableField("payload_id")
    var payloadId: String? = null

    /**
     * 聊天id
     */
    @TableField("chat_id")
    var chatId: String? = null

    @TableField("home_assistant_user_id")
    var homeAssistantUserId: Long? = null

    /**
     * 会话方式：telegram/qq
     */
    @TableField("session_method")
    var sessionMethod: String? = null

    /**
     * 状态 1启用 0停用
     */
    @TableField("status")
    var status: Int? = null

    override fun toString(): String {
        return "HomeAssistantHandshake{" +
        "payloadId=" + payloadId +
        ", chatId=" + chatId +
        ", homeAssistantUserId=" + homeAssistantUserId +
        ", sessionMethod=" + sessionMethod +
        ", status=" + status +
        "}"
    }
}
