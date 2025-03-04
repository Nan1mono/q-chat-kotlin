package com.project.template.module.chat.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.project.template.module.chat.entity.HomeAssistantUser
import com.project.template.module.chat.mapper.HomeAssistantUserMapper
import com.project.template.module.chat.service.HomeAssistantUserService
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
open class HomeAssistantUserServiceImpl : ServiceImpl<HomeAssistantUserMapper, HomeAssistantUser>(), HomeAssistantUserService {

    override fun getByTelegramId(telegramId: Long): HomeAssistantUser? {
        return this.ktQuery().eq(HomeAssistantUser::telegramId, telegramId).one()
    }

    override fun getByQid(qid: Long): HomeAssistantUser? {
        return this.ktQuery().eq(HomeAssistantUser::qqId, qid).one()
    }


}
