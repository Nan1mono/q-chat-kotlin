package com.project.template.module.chat.service;

import com.baomidou.mybatisplus.extension.service.IService
import com.project.template.module.chat.entity.HomeAssistantUser

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lee
 * @since 2025-02-27
 */
interface HomeAssistantUserService : IService<HomeAssistantUser>{

    fun getByTelegramId(telegramId:Long):HomeAssistantUser?

    fun getByQid(qid:Long):HomeAssistantUser?

}
