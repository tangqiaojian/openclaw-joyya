package com.joyya.repository;

import com.joyya.entity.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 收藏数据访问层（Repository）
 * 提供收藏数据的 CRUD 操作
 * 
 * @author Joyya
 * @version 1.0.0
 * @since 2026-03-03
 */
@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {

    /**
     * 根据用户 ID 和 视频 ID 查询收藏记录
     * 
     * @param userId 用户 ID
     * @param videoId 视频 ID
     * @return 收藏记录（如果存在）
     */
    @Query("SELECT c FROM Collection c WHERE c.userId = :userId AND c.videoId = :videoId")
    Optional<Collection> findByUserIdAndVideoId(@Param("userId") Long userId, @Param("videoId") Long videoId);

    /**
     * 根据用户 ID 分页查询收藏记录
     * 
     * @param userId 用户 ID
     * @param pageable 分页参数
     * @return 收藏记录分页列表
     */
    @Query("SELECT c FROM Collection c WHERE c.userId = :userId ORDER BY c.createdAt DESC")
    Page<Collection> findByUserIdOrderByCreatedAtDesc(@Param("userId") Long userId, Pageable pageable);

    /**
     * 根据视频 ID 查询收藏记录列表
     * 
     * @param videoId 视频 ID
     * @return 收藏记录列表
     */
    Iterable<Collection> findByVideoId(Long videoId);

    /**
     * 检查用户是否已收藏视频
     * 
     * @param userId 用户 ID
     * @param videoId 视频 ID
     * @return true-已收藏，false-未收藏
     */
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Collection c WHERE c.userId = :userId AND c.videoId = :videoId")
    boolean existsByUserIdAndVideoId(@Param("userId") Long userId, @Param("videoId") Long videoId);

    /**
     * 删除指定用户的视频收藏记录
     * 
     * @param userId 用户 ID
     * @param videoId 视频 ID
     */
    void deleteByUserIdAndVideoId(Long userId, Long videoId);

    /**
     * 统计用户的收藏数量
     * 
     * @param userId 用户 ID
     * @return 收藏数量
     */
    long countByUserId(Long userId);
}
