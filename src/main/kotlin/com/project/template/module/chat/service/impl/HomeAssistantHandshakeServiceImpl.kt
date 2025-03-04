package com.project.template.module.chat.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.project.template.module.chat.entity.HomeAssistantHandshake
import com.project.template.module.chat.mapper.HomeAssistantHandshakeMapper
import com.project.template.module.chat.service.HomeAssistantHandshakeService
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
open class HomeAssistantHandshakeServiceImpl : ServiceImpl<HomeAssistantHandshakeMapper, HomeAssistantHandshake>(), HomeAssistantHandshakeService {

    override fun getByPayloadId(payloadId: String): HomeAssistantHandshake {
        return this.ktQuery().eq(HomeAssistantHandshake::payloadId, payloadId).one()
    }

}
