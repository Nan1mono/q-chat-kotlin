package com.project.template.module.chat.service;

import com.baomidou.mybatisplus.extension.service.IService
import com.project.template.module.chat.entity.Friend

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lee
 * @since 2025-02-27
 */
interface FriendService : IService<Friend>{

    fun getFriendByName(name:String): List<Friend>

    fun save(simpleName:String, fullName:String, nickName1:String, nickName2:String, nickName3:String):Boolean

}
