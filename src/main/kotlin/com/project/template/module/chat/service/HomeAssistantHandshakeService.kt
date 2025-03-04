package com.project.template.module.chat.service

import com.baomidou.mybatisplus.extension.service.IService
import com.project.template.module.chat.entity.HomeAssistantHandshake

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lee
 * @since 2025-02-27
 */
interface HomeAssistantHandshakeService : IService<HomeAssistantHandshake>{

    fun getByPayloadId(payloadId:String):HomeAssistantHandshake?

}
