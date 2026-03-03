package com.joyya.repository;

import com.joyya.entity.VideoLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 视频点赞数据访问层（Repository）
 * 提供视频点赞数据的 CRUD 操作
 * 
 * @author Joyya
 * @version 1.0.0
 * @since 2026-03-03
 */
@Repository
public interface VideoLikeRepository extends JpaRepository<VideoLike, Long> {

    /**
     * 根据视频 ID 和 用户 ID 查询点赞记录
     * 
     * @param videoId 视频 ID
     * @param userId 用户 ID
     * @return 点赞记录（如果存在）
     */
    @Query("SELECT v FROM VideoLike v WHERE v.videoId = :videoId AND v.userId = :userId")
    Optional<VideoLike> findByVideoIdAndUserId(@Param("videoId") Long videoId, @Param("userId") Long userId);

    /**
     * 根据视频 ID 查询点赞记录列表
     * 
     * @param videoId 视频 ID
     * @return 点赞记录列表
     */
    Iterable<VideoLike> findByVideoId(Long videoId);

    /**
     * 根据用户 ID 查询点赞记录列表
     * 
     * @param userId 用户 ID
     * @return 点赞记录列表
     */
    Iterable<VideoLike> findByUserId(Long userId);

    /**
     * 检查视频是否已被用户点赞
     * 
     * @param videoId 视频 ID
     * @param userId 用户 ID
     * @return true-已点赞，false-未点赞
     */
    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END FROM VideoLike v WHERE v.videoId = :videoId AND v.userId = :userId")
    boolean existsByVideoIdAndUserId(@Param("videoId") Long videoId, @Param("userId") Long userId);

    /**
     * 删除指定视频的用户点赞记录
     * 
     * @param videoId 视频 ID
     * @param userId 用户 ID
     */
    void deleteByVideoIdAndUserId(Long videoId, Long userId);
}
