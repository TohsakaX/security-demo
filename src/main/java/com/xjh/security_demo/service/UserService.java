package com.xjh.security_demo.service;

import com.xjh.security_demo.entity.User;

public interface UserService {

    User getUserById(String id);


    User getUserByName(String name);
}
