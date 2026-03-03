package com.joyya.repository;

import com.joyya.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 用户数据访问层（Repository）
 * 提供用户数据的基本 CRUD 操作和自定义查询方法
 * 
 * @author Joyya
 * @version 1.0.0
 * @since 2026-03-03
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据用户名查询用户
     * 
     * @param username 用户名
     * @return 用户对象（如果存在）
     */
    Optional<User> findByUsername(String username);

    /**
     * 根据邮箱地址查询用户
     * 
     * @param email 邮箱地址
     * @return 用户对象（如果存在）
     */
    Optional<User> findByEmail(String email);

    /**
     * 检查用户名是否存在
     * 
     * @param username 用户名
     * @return true-用户名存在，false-用户名不存在
     */
    boolean existsByUsername(String username);

    /**
     * 检查邮箱是否存在
     * 
     * @param email 邮箱地址
     * @return true-邮箱存在，false-邮箱不存在
     */
    boolean existsByEmail(String email);
}
