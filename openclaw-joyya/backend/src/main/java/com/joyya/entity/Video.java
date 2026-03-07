package com.joyya.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 视频实体类
 * 对应数据库中的 videos 表
 * 
 * @author Joyya
 * @version 1.0.0
 * @since 2026-03-03
 */
@Entity
@Table(name = "videos")
@Data
public class Video {

    /**
     * 视频 ID，主键，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 视频标题，最大长度 200 字符
     */
    @Column(nullable = false, length = 200)
    private String title;

    /**
     * 视频简介，支持文本内容
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * 封面图 URL
     */
    @Column(name = "cover", nullable = false, length = 255)
    private String cover;

    /**
     * 视频播放 URL
     */
    @Column(name = "video_url", nullable = false, length = 500)
    private String videoUrl;

    /**
     * 视频时长（秒）
     */
    @Column(name = "duration", nullable = false)
    private Integer duration;

    /**
     * 标签列表（多对多关联）
     */
    @ManyToMany
    @JoinTable(
        name = "video_tags",
        joinColumns = @JoinColumn(name = "video_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();

    /**
     * 分类 ID，关联 categories 表
     */
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    /**
     * 上传者用户 ID
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 播放次数
     */
    @Column(name = "view_count", nullable = false)
    private Integer viewCount;

    /**
     * 点赞数
     */
    @Column(name = "like_count", nullable = false)
    private Integer likeCount;

    /**
     * 踩数
     */
    @Column(name = "dislike_count", nullable = false)
    private Integer dislikeCount;

    /**
     * 分享次数
     */
    @Column(name = "share_count", nullable = false)
    private Integer shareCount;

    /**
     * 收藏/投币数
     */
    @Column(name = "coin_count", nullable = false)
    private Integer coinCount;

    /**
     * 状态：0-待审核，1-已发布，2-已下架
     */
    @Column(nullable = false)
    private Integer status;

    /**
     * 是否推荐：0-否，1-是
     */
    @Column(name = "is_recommend", nullable = false)
    private Integer isRecommend;

    /**
     * 是否热门：0-否，1-是
     */
    @Column(name = "is_hot", nullable = false)
    private Integer isHot;

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
     * 自动设置创建时间
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    /**
     * 实体更新前回调
     * 自动更新时间戳
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
