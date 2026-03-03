package com.joyya.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 视频点赞实体类
 * 对应数据库中的 video_likes 表
 * 
 * @author Joyya
 * @version 1.0.0
 * @since 2026-03-03
 */
@Entity
@Table(name = "video_likes")
@Data
public class VideoLike {

    /**
     * 点赞 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 视频 ID
     */
    @Column(name = "video_id", nullable = false)
    private Long videoId;

    /**
     * 用户 ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 点赞时间
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
