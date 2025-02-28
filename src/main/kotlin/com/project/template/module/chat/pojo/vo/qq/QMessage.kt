package com.project.template.module.chat.pojo.vo.qq

/**
 * QQ消息主题类，定义了QQ消息的基本格式
 */
class QMessage(
    var type: String? = null,
    var data: QMessageData? = QMessageData(),
)