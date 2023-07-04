package com.xjh.security_demo;


import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTHeader;
import cn.hutool.jwt.JWTUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class SecurityDemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void testByBCRYPT(){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("123456"));
	}

	@Test
	public void testByJWT(){
		Map<String, Object> map = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("uid", Integer.parseInt("123"));
				put("expire_time", System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15);
			}
		};
		String token = JWTUtil.createToken(map, "123456".getBytes());
		System.out.println(token);
		JWT jwt = JWTUtil.parseToken(token);
		System.out.println(jwt.getHeader(JWTHeader.TYPE));
		System.out.println(jwt.getPayloads());
		System.out.println(JWTUtil.verify(token, "123456".getBytes()));
	}
}
