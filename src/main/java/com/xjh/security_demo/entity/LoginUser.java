package com.xjh.security_demo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements UserDetails {

    private User user;

    private List<String> permissions;

    //存储SpringSecurity所需要的权限信息的集合
    // 该成员变量不进行序列化
    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> authorities;

    public LoginUser(User user, List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }

    /**
     * Title: getAuthorities
     * @author XJH
     * @description 返回权限List<SimpleGrantedAuthority>,SimpleGrantedAuthority存放权限字符串
     * @createTime  2023/7/9
     * @param
     * @return java.util.Collection<? extends org.springframework.security.core.GrantedAuthority>
     * @version V1.0
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 第一次时为空，会执行后面的stream流，第二次则直接返回
        if(authorities != null) {
            return authorities;
        }
        if(permissions == null) {
            return null;
        }
        // 把permissions中字符串类型的权限信息转换成GrantedAuthority对象存入authorities中
        authorities = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
