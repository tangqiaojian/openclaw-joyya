package com.joyya.service;

import com.joyya.dto.LoginRequest;
import com.joyya.entity.User;
import com.joyya.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 用户服务类
 * 处理用户相关的业务逻辑
 * 
 * @author Joyya
 * @version 1.0.0
 * @since 2026-03-03
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 根据用户名查找用户
     * 
     * @param username 用户名
     * @return 用户对象（如果存在）
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * 注册用户
     * 检查用户名唯一性，加密密码后保存
     * 
     * @param username 用户名
     * @param email 邮箱地址
     * @param password 原始密码（将被加密）
     * @return 注册是否成功
     *         - true: 注册成功
     *         - false: 用户名已存在
     * 
     * @throws RuntimeException 如果数据库操作失败
     */
    public boolean registerUser(String username, String email, String password) {
        // 检查用户名是否已存在
        if (userRepository.existsByUsername(username)) {
            return false;
        }

        // 创建新用户对象
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        // 密码加密存储（BCrypt）
        user.setPassword(passwordEncoder.encode(password));
        
        // 保存到数据库
        userRepository.save(user);
        return true;
    }

    /**
     * 用户登录认证
     * 验证用户名和密码是否正确
     * 
     * @param loginRequest 登录请求对象
     * @return 认证成功的用户对象（如果成功）
     *         - 成功：返回 User 对象
     *         - 失败：返回 null
     * 
     * @apiNote
     *         1. 根据用户名查询用户
     *         2. 使用 BCrypt 验证密码
     *         3. 密码匹配返回用户，否则返回 null
     */
    public User authenticate(LoginRequest loginRequest) {
        // 根据用户名查询用户
        Optional<User> userOpt = userRepository.findByUsername(loginRequest.getUsername());
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // 使用 BCrypt 验证密码
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                return user;
            }
        }
        
        // 用户名不存在或密码错误
        return null;
    }
}
