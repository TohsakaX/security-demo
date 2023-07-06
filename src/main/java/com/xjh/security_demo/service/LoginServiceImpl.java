package com.xjh.security_demo.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.jwt.JWTUtil;
import com.xjh.security_demo.entity.LoginUser;
import com.xjh.security_demo.entity.User;
import com.xjh.security_demo.utils.RedisUtil;
import com.xjh.security_demo.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public String login(User user) {
        // AuthenticationManager authenticate进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //如果认证没通过，给出对应的提示
        if (ObjectUtil.isNull(authenticate)){
            throw new RuntimeException("登录失败");
        }
        //如果认证通过了，使用userid生成一个jwt jwt存入ResultVo返回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        // 把完整的用户信息存入redis userid作为key
        String id = loginUser.getUser().getId();
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("name",loginUser.getUser().getName());
        String token = JWTUtil.createToken(map, "userInfo".getBytes());
        // 将token缓存进redis
        redisUtil.set("login:"+id,token,3600);
        return token;
    }

    @Override
    public void logout() {
        // 获取SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User principal = (User)authentication.getPrincipal();
        String userId = principal.getId();
        // 删除redis中的值
        redisUtil.del("login:" + userId);
    }
}
