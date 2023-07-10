package com.xjh.security_demo.handler;

import com.alibaba.fastjson.JSON;
import com.xjh.security_demo.utils.WebUtils;
import com.xjh.security_demo.vo.ResultVo;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Title: AuthenticationEntryPointImpl
 * @author XJH
 * @description 认证过程中出现的异常会被封装成AuthenticationException然后调用AuthenticationEntryPoint对象的方法去进行异常处理。
 * @createTime  2023/7/10
 * @version V1.0
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResultVo<String> resultVo = ResultVo.err(HttpStatus.FORBIDDEN.value(), "权限不足");
        String jsonString = JSON.toJSONString(resultVo);
        WebUtils.renderString(response,jsonString);
    }
}
