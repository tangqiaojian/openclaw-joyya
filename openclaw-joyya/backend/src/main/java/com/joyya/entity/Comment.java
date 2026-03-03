package com.joyya.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 评论实体类
 * 对应数据库中的 comments 表
 * 
 * @author Joyya
 * @version 1.0.0
 * @since 2026-03-03
 */
@Entity
@Table(name = "comments")
@Data
public class Comment {

    /**
     * 评论 ID
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
     * 评论用户 ID
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 父评论 ID，0 表示一级评论
     */
    @Column(name = "parent_id", nullable = false)
    private Long parentId;

    /**
     * 评论内容
     */
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    /**
     * 点赞数
     */
    @Column(name = "like_count", nullable = false)
    private Integer likeCount;

    /**
     * 回复数
     */
    @Column(name = "reply_count", nullable = false)
    private Integer replyCount;

    /**
     * 状态：0-已删除，1-正常
     */
    @Column(nullable = false)
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * 实体创建前回调
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    /**
     * 实体更新前回调
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
