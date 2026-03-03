package com.joyya.controller;

import com.joyya.dto.LoginRequest;
import com.joyya.entity.User;
import com.joyya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器
 * 处理用户登录和注册相关的 HTTP 请求
 * 
 * @author Joyya
 * @version 1.0.0
 * @since 2026-03-03
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // 允许跨域请求，开发时可配置为具体域名
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录接口
     * POST /api/auth/login
     * 
     * @param request 登录请求对象，包含用户名和密码
     * @return 登录结果响应
     *         - 成功：返回 token 和用户信息
     *         - 失败：返回错误信息
     * 
     * @apiNote 
     *         密码验证使用 BCrypt 加密比对
     *         Token 生成逻辑待实现（目前返回 mock token）
     * 
     * @example
     *         {
     *           "username": "admin",
     *           "password": "123456"
     *         }
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        // 调用服务层进行身份验证
        User user = userService.authenticate(request);
        
        if (user != null) {
            // 认证成功
            // TODO: 生成 JWT token
            String token = "mock-jwt-token-" + user.getId();
            
            response.put("success", true);
            response.put("message", "登录成功");
            response.put("token", token);
            response.put("data", Map.of(
                "id", user.getId(),
                "username", user.getUsername(),
                "email", user.getEmail()
            ));
            
            return ResponseEntity.ok(response);
        } else {
            // 认证失败：用户名或密码错误
            response.put("success", false);
            response.put("message", "用户名或密码错误");
            
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 用户注册接口
     * POST /api/auth/register
     * 
     * @param request 注册请求对象，包含用户名、邮箱和密码
     * @return 注册结果响应
     *         - 成功：注册成功，可立即登录
     *         - 失败：用户名已存在或参数错误
     * 
     * @apiNote
     *         密码在保存前会进行 BCrypt 加密
     *         用户名和邮箱会检查唯一性
     * 
     * @example
     *         {
     *           "username": "newuser",
     *           "email": "user@example.com",
     *           "password": "123456"
     *         }
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(
            @RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        
        String username = request.get("username");
        String email = request.get("email");
        String password = request.get("password");
        
        // 参数校验
        if (username == null || email == null || password == null) {
            response.put("success", false);
            response.put("message", "参数不能为空");
            return ResponseEntity.badRequest().body(response);
        }
        
        // 调用服务层注册新用户
        boolean success = userService.registerUser(username, email, password);
        
        if (success) {
            response.put("success", true);
            response.put("message", "注册成功");
            
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "用户名已存在");
            
            return ResponseEntity.badRequest().body(response);
        }
    }
}
