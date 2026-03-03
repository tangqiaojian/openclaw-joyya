package com.joyya.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 标签实体类
 * 对应数据库中的 tags 表
 * 
 * @author Joyya
 * @version 1.0.0
 * @since 2026-03-03
 */
@Entity
@Table(name = "tags")
@Data
public class Tag {

    /**
     * 标签 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 标签名称，唯一
     */
    @Column(nullable = false, unique = true, length = 50)
    private String name;

    /**
     * 标签描述
     */
    @Column(length = 200)
    private String description;

    /**
     * 排序顺序
     */
    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    /**
     * 创建时间
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * 关联的视频列表
     */
    @ManyToMany(mappedBy = "tags")
    private List<Video> videos = new ArrayList<>();

    /**
     * 实体创建前回调
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
