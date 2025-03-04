package com.project.template.module.chat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.project.template.module.chat.entity.Friend
import com.project.template.module.chat.mapper.FriendMapper
import com.project.template.module.chat.service.FriendService
import org.springframework.stereotype.Service

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lee
 * @since 2025-02-27
 */
@Service
open class FriendServiceImpl : ServiceImpl<FriendMapper, Friend>(), FriendService {
    override fun getFriendByName(name: String): List<Friend> {
        return this.ktQuery().eq(Friend::simpleSpelling, name)
            .or().eq(Friend::fullName, name)
            .or().eq(Friend::nickName1, name)
            .or().eq(Friend::nickName2, name)
            .or().eq(Friend::nickName3, name)
            .list()
    }

    override fun save(
        simpleName: String, fullName: String, nickName1: String, nickName2: String, nickName3: String
    ): Boolean {
        val friend = Friend().apply {
            this.simpleSpelling = simpleName
            this.fullName = fullName
            this.nickName1 = nickName1
            this.nickName2 = nickName2
            this.nickName3 = nickName3
            this.status = 1
        }
        return this.save(friend)
    }


}
