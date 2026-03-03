package com.joyya.dto;

import lombok.Data;

/**
 * 登录请求 DTO（Data Transfer Object）
 * 用于封装登录接口的请求参数
 * 
 * @author Joyya
 * @version 1.0.0
 * @since 2026-03-03
 */
@Data
public class LoginRequest {

    /**
     * 用户名
     * 用于用户身份识别
     */
    private String username;

    /**
     * 密码
     * 用于密码验证（不加密传输）
     */
    private String password;
}
