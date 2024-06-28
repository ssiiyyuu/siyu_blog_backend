package com.siyu.service_admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.siyu.service_admin.entity.dto.UserLoginDto;
import com.siyu.service_admin.service.UserService;
import com.siyu.service_base.result.Result;

import io.swagger.annotations.Api;

@Api(description="登录用户信息管理")
@RestController
@RequestMapping("/service_admin")
public class LoginController {
    
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDto user) {
        return Result.ok().data("token", userService.login(user.getUsername(), user.getPassword()));
    }

    @GetMapping("/info")
    public Result getInfo(@RequestParam String token) {
        return Result.ok().data(userService.info(token));
    }

    @PostMapping("/logout")
    public Result logout() {
        return Result.ok();
    }

}
