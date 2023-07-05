package com.xjh.security_demo.controller;

import com.xjh.security_demo.entity.User;
import com.xjh.security_demo.service.LoginService;
import com.xjh.security_demo.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/user/login")
    public ResultVo<String> login(@RequestBody User user){
        // 登录
        return ResultVo.ok(loginService.login(user));
    }
}
