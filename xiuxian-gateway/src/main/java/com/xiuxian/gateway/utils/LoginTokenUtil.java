package com.xiuxian.gateway.utils;
 
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
 
/**
 * 登录token工具类
 */
public class LoginTokenUtil {

	/** 登录成功后的回调接口的token参数名称 */
	public static final String HEADER_TOKEN_NAME = "Authorization";
	public static final String WS_TOKEN_NAME = "token";
	public static final String USER_ID = "userId";
	/**
	 * 校验登录token是否有效
	 * @param loginToken 登录token
	 * @return
	 */
	public static boolean validateLoginToken(String loginToken) {
		if (StringUtils.isBlank(loginToken)) {
			// do something
			return true;
		}
		return false;
	}
 
}