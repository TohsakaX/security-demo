package com.xjh.security_demo.handler;

import com.alibaba.fastjson.JSON;
import com.xjh.security_demo.utils.WebUtils;
import com.xjh.security_demo.vo.ResultVo;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Title: AuthenticationEntryPointImpl
 * @author XJH
 * @description 授权过程中出现的异常会被封装成AccessDeniedException然后调用AccessDeniedHandler对象的方法去进行异常处理
 * @createTime  2023/7/10
 * @version V1.0
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    // 处理异常
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResultVo<String> resultVo = ResultVo.err(HttpStatus.UNAUTHORIZED.value(), "认证失败请重新登录");
        String json = JSON.toJSONString(resultVo);
        // 处理异常
        WebUtils.renderString(response,json);
    }
}
