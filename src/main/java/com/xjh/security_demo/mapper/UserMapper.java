package com.xjh.security_demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xjh.security_demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User>{
}
