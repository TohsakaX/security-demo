package com.xjh.security_demo.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSON;

import com.xjh.security_demo.entity.LoginUser;
import com.xjh.security_demo.entity.User;
import com.xjh.security_demo.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Title: JwtAuthenticationTokenFilter
 * @author XJH
 * @description jwt认证过滤器
 * @createTime  2023/7/6
 * @version V1.0
 */
@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * Title: doFilterInternal
     * @author XJH
     * @description token认证过滤,并且将用户信息和权限信息封装到Authentication中
     * @createTime  2023/7/8
     * @param
     * @return void
     * @version V1.0
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)){
            // 放行
            filterChain.doFilter(request,response);
            log.error("用户未登录！");
            return;
        }
        // 认证token，解析token
        if (!JWTUtil.verify(token,"userInfo".getBytes())){
            log.error("token非法！");
            throw new RuntimeException("token非法！");
        }
        JSONObject payloads = JWTUtil.parseToken(token).getPayloads();
        String userId = payloads.getStr("id");
        String name = payloads.getStr("name");
        User user = new User();
        user.setId(userId);
        user.setName(name);
        // 从redis中获取用户信息
        String userKey = "login:" + userId;
        /*String userInfo = redisUtil.get(userKey);*/
        String userInfo = stringRedisTemplate.opsForValue().get(userKey);
        com.alibaba.fastjson.JSONObject userMap = JSON.parseObject(userInfo);
        if(userMap == null){
            log.error("登录过期！");
            throw new RuntimeException("登录过期！");
        }
        List<String> authorities = userMap.getJSONArray("authorities")!=null ? userMap.getJSONArray("authorities").toJavaList(String.class) : null;

        LoginUser loginUser = new LoginUser(user,authorities);
        // 存入SecurityContextHolder
        // TODO 获取权限信息封装到Authentication中
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // filterChain.doFilter(request,response) 放行
        filterChain.doFilter(request,response);
    }
}
