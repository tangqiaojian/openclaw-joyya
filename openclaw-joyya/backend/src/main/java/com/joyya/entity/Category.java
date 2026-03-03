package com.joyya.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 视频分类实体类
 * 对应数据库中的 categories 表
 * 
 * @author Joyya
 * @version 1.0.0
 * @since 2026-03-03
 */
@Entity
@Table(name = "categories")
@Data
public class Category {

    /**
     * 分类 ID，主键，自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 分类名称
     */
    @Column(nullable = false, length = 50)
    private String name;

    /**
     * 分类描述
     */
    @Column(length = 200)
    private String description;

    /**
     * 排序顺序
     */
    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    /**
     * 父分类 ID，0 表示顶级分类
     */
    @Column(name = "parent_id", nullable = false)
    private Long parentId;

    /**
     * 分类图标 URL
     */
    @Column(length = 255)
    private String icon;

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
