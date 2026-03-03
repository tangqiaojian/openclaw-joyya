package com.joyya.repository;

import com.joyya.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 视频数据访问层（Repository）
 * 提供视频数据的 CRUD 操作和自定义查询方法
 * 
 * @author Joyya
 * @version 1.0.0
 * @since 2026-03-03
 */
@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    /**
     * 根据用户 ID 查询视频
     * 
     * @param userId 用户 ID
     * @return 用户视频列表
     */
    List<Video> findByUserId(Long userId);

    /**
     * 根据分类 ID 查询视频
     * 
     * @param categoryId 分类 ID
     * @return 分类下的视频列表
     */
    List<Video> findByCategoryId(Long categoryId);

    /**
     * 根据标题模糊查询视频
     * 
     * @param title 标题关键词
     * @return 匹配的视频列表
     */
    List<Video> findByTitleContaining(String title);

    /**
     * 分页查询已发布的热门视频
     * 
     * @param pageable 分页参数
     * @return 热门视频分页列表
     */
    @Query("SELECT v FROM Video v WHERE v.status = 1 AND v.isHot = 1 ORDER BY v.likeCount DESC")
    Page<Video> findHotVideos(Pageable pageable);

    /**
     * 分页查询视频列表（支持搜索和分类筛选）
     * 
     * @param categoryId 分类 ID
     * @param keyword 搜索关键词
     * @param pageable 分页参数
     * @return 视频分页列表
     */
    @Query("SELECT v FROM Video v " +
           "WHERE v.status = 1 " +
           "AND (:categoryId IS NULL OR v.category.id = :categoryId) " +
           "AND (:keyword IS NULL OR v.title LIKE %:keyword%) " +
           "ORDER BY v.createdAt DESC")
    Page<Video> searchVideos(
        @Param("categoryId") Long categoryId,
        @Param("keyword") String keyword,
        Pageable pageable
    );

    /**
     * 获取最新发布的视频
     * 
     * @param pageable 分页参数
     * @return 最新视频列表
     */
    @Query("SELECT v FROM Video v WHERE v.status = 1 ORDER BY v.createdAt DESC")
    Page<Video> findLatestVideos(Pageable pageable);
}
