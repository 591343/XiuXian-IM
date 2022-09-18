/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.xiuxian.websocket.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户Token
 * 
 * @author Mark sunlightcs@gmail.com
 */
@Data
@ToString
public class TokenEntity implements Serializable {
	private static final long serialVersionUID = 1L;


	private Long id;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 用户token
	 */
	private String token;
	/**
	 * 过期时间
	 */
	private Date expireDate;
	/**
	 * 更新时间
	 */
	private Date updateDate;

}