package com.xjh.security_demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @GetMapping("hello")
    @PreAuthorize("hasAnyAuthority('admin:hell','admin:hello')")
    public String hello(){
        return "hello xjh";
    }

    @GetMapping("/vip")
    @PreAuthorize("hasAuthority('admin:vip')")
    public String index(){
        return "welcome vip !";
    }
}
