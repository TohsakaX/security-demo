package com.xjh.security_demo.controller;

import com.xjh.security_demo.entity.User;
import com.xjh.security_demo.service.LoginService;
import com.xjh.security_demo.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/user/login")
    public ResultVo<String> login(@RequestBody User user){
        // 登录
        try {
            return ResultVo.ok(loginService.login(user));
        }catch (Exception e){
            log.error(e.toString());
            return ResultVo.err(e.getMessage());
        }
    }

    @GetMapping("/user/logout")
    public ResultVo<String> logout(){
        // 注销
        try {
            loginService.logout();
            return ResultVo.ok("注销成功！");
        }catch (Exception e){
            log.error(e.toString());
            return ResultVo.err(e.getMessage());
        }
    }
}
