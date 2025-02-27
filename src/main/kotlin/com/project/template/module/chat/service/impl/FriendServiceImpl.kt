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

}
