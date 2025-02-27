package com.project.template.config

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import org.apache.ibatis.reflection.MetaObject
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
open class MyBatisMetaHandler: MetaObjectHandler {

    override fun insertFill(metaObject: MetaObject?) {
        this.strictInsertFill(metaObject, "createOn",LocalDateTime::class.java, LocalDateTime.now())
        this.strictInsertFill(metaObject, "updateOn",LocalDateTime::class.java, LocalDateTime.now())
    }

    override fun updateFill(metaObject: MetaObject?) {
        this.strictInsertFill(metaObject, "updateOn",LocalDateTime::class.java, LocalDateTime.now())
    }


}