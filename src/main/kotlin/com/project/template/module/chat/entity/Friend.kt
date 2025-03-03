package com.project.template.module.chat.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.google.common.collect.Lists
import com.project.template.module.base.entity.BaseEntity;
import org.apache.commons.collections4.CollectionUtils

/**
 * <p>
 *
 * </p>
 *
 * @author lee
 * @since 2025-02-27
 */
@TableName("friend")
class Friend : BaseEntity() {

    /**
     * uid
     */
    @TableField("uid")
    var uid: String? = null

    /**
     * uni
     */
    @TableField("uin")
    var uin: Long? = null

    /**
     * 姓名拼音简拼
     */
    @TableField("simple_spelling")
    var simpleSpelling: String? = null

    /**
     * 全名
     */
    @TableField("full_name")
    var fullName: String? = null

    /**
     * 昵称1
     */
    @TableField("nick_name1")
    var nickName1: String? = null

    /**
     * 昵称2
     */
    @TableField("nick_name2")
    var nickName2: String? = null

    /**
     * 昵称3
     */
    @TableField("nick_name3")
    var nickName3: String? = null

    /**
     * 状态 1启用 0停用
     */
    @TableField("status")
    var status: Int? = null

    override fun toString(): String {
        return "Friend{" +
                "uid=" + uid +
                ", uin=" + uin +
                ", simpleSpelling=" + simpleSpelling +
                ", fullName=" + fullName +
                ", nickName1=" + nickName1 +
                ", nickName2=" + nickName2 +
                ", nickName3=" + nickName3 +
                ", status=" + status +
                "}"
    }

    companion object {
        fun convert2Msg(friendList: List<Friend>): String {
            if (CollectionUtils.isEmpty(friendList)) {
                return "没有记录请先添加"
            }
            val list = Lists.newArrayList<String>()
            for (friend in friendList) {
                list.add(convert2Msg(friend))
            }
            return list.joinToString("\n")
        }

        fun convert2Msg(friend: Friend): String {
            return """
                姓名：${friend.fullName}，
                拼音：${friend.simpleSpelling}
                昵称1：${friend.nickName1}
                昵称2：${friend.nickName2}
                昵称3：${friend.nickName3}
                """.trimIndent()
        }
    }
}
