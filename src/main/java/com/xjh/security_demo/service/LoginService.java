package com.xjh.security_demo.service;

import com.xjh.security_demo.entity.User;
import com.xjh.security_demo.vo.ResultVo;

public interface LoginService {
    String login(User user);

    void logout();
}
