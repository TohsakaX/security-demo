package com.xjh.security_demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @GetMapping("hello")
    public String hello(){
        return "hello xjh";
    }

    @GetMapping("/vip")
    public String index(){
        return "welcome vip !";
    }
}
