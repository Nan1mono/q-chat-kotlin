package com.project.template.module.chat.controller;

import com.project.template.common.result.Result2
import com.project.template.module.chat.entity.Friend
import com.project.template.module.chat.service.FriendService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lee
 * @since 2025-02-27
 */
@RestController
@RequestMapping("/api/v3/friend")
class FriendController @Autowired constructor(

    private val friendService: FriendService,

    ) {

    @GetMapping("/id")
    fun getOne(@RequestParam id: Long): Result2<Friend> {
        val byId = friendService.getById(id)
        return Result2.success(byId);
    }

}

