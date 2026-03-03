package com.joyya.repository;

import com.joyya.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 评论数据访问层（Repository）
 * 提供评论数据的 CRUD 操作和自定义查询方法
 * 
 * @author Joyya
 * @version 1.0.0
 * @since 2026-03-03
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * 根据视频 ID 查询评论
     * 
     * @param videoId 视频 ID
     * @param pageable 分页参数
     * @return 评论分页列表
     */
    Page<Comment> findByVideoIdOrderByCreatedAtDesc(Long videoId, Pageable pageable);

    /**
     * 根据用户 ID 查询评论
     * 
     * @param userId 用户 ID
     * @param pageable 分页参数
     * @return 用户评论分页列表
     */
    Page<Comment> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    /**
     * 查询视频的一级评论（parent_id = 0）
     * 
     * @param videoId 视频 ID
     * @param pageable 分页参数
     * @return 一级评论列表
     */
    @Query("SELECT c FROM Comment c WHERE c.videoId = :videoId AND c.parentId = 0 " +
           "ORDER BY c.createdAt DESC")
    Page<Comment> findTopLevelComments(Long videoId, Pageable pageable);

    /**
     * 查询评论的回复
     * 
     * @param parentCommentId 父评论 ID
     * @return 回复评论列表
     */
    List<Comment> findByParentIdOrderByCreatedAtAsc(Long parentCommentId);

    /**
     * 统计评论数量
     * 
     * @param videoId 视频 ID
     * @return 评论总数
     */
    long countByVideoId(Long videoId);
}
