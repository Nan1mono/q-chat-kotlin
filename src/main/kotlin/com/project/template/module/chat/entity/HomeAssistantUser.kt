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
@TableName("home_assistant_user")
class HomeAssistantUser : BaseEntity() {

    /**
     * 消息id
     */
    @TableField("message_id")
    var messageId: Long? = null

    /**
     * QQ账号
     */
    @TableField("qq_id")
    var qqId: Long? = null

    /**
     * 电报id
     */
    @TableField("telegram_id")
    var telegramId: Long? = null

    /**
     * HA设备主要识别标识，由HA生成
     */
    @TableField("primary_config_entry")
    var primaryConfigEntry: String? = null

    /**
     * 状态 1启用 0停用
     */
    @TableField("status")
    var status: Int? = null

    override fun toString(): String {
        return "HomeAssistantUser{" +
        "messageId=" + messageId +
        ", qqId=" + qqId +
        ", telegramId=" + telegramId +
        ", primaryConfigEntry=" + primaryConfigEntry +
        ", status=" + status +
        "}"
    }
}
