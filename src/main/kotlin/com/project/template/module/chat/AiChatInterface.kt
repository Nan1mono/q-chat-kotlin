package com.project.template.module.chat

interface AiChatInterface {

    fun chat(message:String):String

    fun getAccessToken():String

}