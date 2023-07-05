package com.xjh.security_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 配置请求哪些资源时不需要做认证
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**","/css/**","/images/**");
    }

    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests().anyRequest()
                .authenticated()
                .and()
                // 开启表单验证
                .formLogin()
                .loginPage("/login.html")
                .permitAll()
                // 当登录成功后，指点跳转到首页
                .defaultSuccessUrl("/index.html",true)
                // post请求的登录接口
                .loginProcessingUrl("/login")
                // 登录失败，用户名或密码错误
                .failureForwardUrl("/error.html")
                // 登陆时携带的用户名和密码的表单的键
                // 要认证的用户参数名，默认username
                .usernameParameter("username")
                //要认证的密码参数名，默认password
                .passwordParameter("password")
                .and()
                //配置注销
                .logout()
                //注销接口
                .logoutUrl("/logout")
                //注销成功后跳转到的接口
                .logoutSuccessUrl("/login.html")
                .permitAll()
                //删除自定义的cookie
                .deleteCookies("myCookie")
                .and()
                //注意:需禁用crsf防护功能,否则登录不成功
                .csrf()
                .disable();
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        //关闭csrf
                .csrf().disable()
                // 不通iSession获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 运行匿名访问
                .antMatchers("/user/login").anonymous()
                // 除上面的所有请求全部需要鉴权认证
                .anyRequest().authenticated();
    }

    @Bean
    public PasswordEncoder password(){
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
