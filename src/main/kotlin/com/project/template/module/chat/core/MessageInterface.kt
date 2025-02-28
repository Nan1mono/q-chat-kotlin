package com.project.template.module.chat.core

import java.util.regex.Pattern

interface MessageInterface {

    fun parsingCommand(message: String, regex: String) {
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(message)
        if (!matcher.find()){

        }
    }

}