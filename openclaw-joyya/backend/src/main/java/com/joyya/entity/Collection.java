package com.joyya.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 收藏实体类
 * 对应数据库中的 collections 表
 * 
 * @author Joyya
 * @version 1.0.0
 * @since 2026-03-03
 */
@Entity
@Table(name = "collections")
@Data
public class Collection {

    /**
     * 收藏 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户 ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 视频 ID
     */
    @Column(name = "video_id", nullable = false)
    private Long videoId;

    /**
     * 收藏时间
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * 实体创建前回调
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
