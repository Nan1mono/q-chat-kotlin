package com.project.template.module.chat.service.impl;

import com.project.template.module.chat.entity.Friend;
import com.project.template.module.chat.mapper.FriendMapper;
import com.project.template.module.chat.service.FriendService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
    override fun getFriendByName(name: String): List<Friend>  {
        return this.lambdaQuery().eq(Friend::simpleSpelling, name)
            .or().eq(Friend::fullName, name)
            .or().eq(Friend::nickName1, name)
            .or().eq(Friend::nickName2, name)
            .or().eq(Friend::nickName3, name)
            .list();
    }

}
