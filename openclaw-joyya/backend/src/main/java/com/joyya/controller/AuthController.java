package com.joyya.controller;

import com.joyya.dto.LoginRequest;
import com.joyya.entity.User;
import com.joyya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        User user = userService.authenticate(request);
        
        if (user != null) {
            // TODO: Generate JWT token
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
            response.put("success", false);
            response.put("message", "用户名或密码错误");
            
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(
            @RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        
        String username = request.get("username");
        String email = request.get("email");
        String password = request.get("password");
        
        if (username == null || email == null || password == null) {
            response.put("success", false);
            response.put("message", "参数不能为空");
            return ResponseEntity.badRequest().body(response);
        }
        
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
