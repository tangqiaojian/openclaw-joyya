package com.joyya.service;

import com.joyya.entity.Comment;
import com.joyya.entity.User;
import com.joyya.entity.Video;
import com.joyya.repository.CommentRepository;
import com.joyya.repository.UserRepository;
import com.joyya.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 评论服务类
 * 处理评论相关的业务逻辑
 * 
 * @author Joyya
 * @version 1.0.0
 * @since 2026-03-03
 */
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoRepository videoRepository;

    /**
     * 根据 ID 获取评论
     * 
     * @param commentId 评论 ID
     * @return 评论对象（如果存在）
     */
    @Transactional(readOnly = true)
    public Optional<Comment> getCommentById(Long commentId) {
        return commentRepository.findById(commentId);
    }

    /**
     * 获取视频的一级评论列表
     * 
     * @param videoId 视频 ID
     * @param page 页码
     * @param size 每页数量
     * @return 一级评论分页列表
     */
    @Transactional(readOnly = true)
    public Page<Comment> getTopLevelComments(Long videoId, int page, int size) {
        // 验证视频是否存在
        if (!videoRepository.existsById(videoId)) {
            throw new IllegalArgumentException("视频不存在");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return commentRepository.findTopLevelComments(videoId, pageable);
    }

    /**
     * 获取视频的评论列表（包含回复）
     * 
     * @param videoId 视频 ID
     * @param page 页码
     * @param size 每页数量
     * @return 评论分页列表
     */
    @Transactional(readOnly = true)
    public Page<Comment> getComments(Long videoId, int page, int size) {
        // 验证视频是否存在
        if (!videoRepository.existsById(videoId)) {
            throw new IllegalArgumentException("视频不存在");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return commentRepository.findByVideoIdOrderByCreatedAtDesc(videoId, pageable);
    }

    /**
     * 获取评论的回复列表
     * 
     * @param parentCommentId 父评论 ID
     * @return 回复评论列表
     */
    @Transactional(readOnly = true)
    public List<Comment> getReplies(Long parentCommentId) {
        // 验证父评论是否存在
        if (!commentRepository.existsById(parentCommentId)) {
            throw new IllegalArgumentException("父评论不存在");
        }

        return commentRepository.findByParentIdOrderByCreatedAtAsc(parentCommentId);
    }

    /**
     * 统计视频的评论数量
     * 
     * @param videoId 视频 ID
     * @return 评论总数
     */
    @Transactional(readOnly = true)
    public long countComments(Long videoId) {
        return commentRepository.countByVideoId(videoId);
    }

    /**
     * 发表评论
     * 
     * @param videoId 视频 ID
     * @param userId 用户 ID
     * @param content 评论内容
     * @param parentId 父评论 ID（0 表示一级评论）
     * @return 创建的评论对象
     */
    @Transactional
    public Comment createComment(Long videoId, Long userId, String content, Long parentId) {
        // 验证视频和用户是否存在
        if (!videoRepository.existsById(videoId)) {
            throw new IllegalArgumentException("视频不存在");
        }

        Optional<User> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            throw new IllegalArgumentException("用户不存在");
        }

        Comment comment = new Comment();
        comment.setVideoId(videoId);
        comment.setUser(userOpt.get());
        comment.setParentId(parentId != null ? parentId : 0L);
        comment.setContent(content);
        comment.setLikeCount(0);
        comment.setReplyCount(0);
        comment.setStatus(1);

        Comment savedComment = commentRepository.save(comment);

        // 如果是一级评论，更新回复数
        if (parentId != null && parentId != 0L) {
            Comment parentComment = commentRepository.findById(parentId)
                    .orElseThrow(() -> new IllegalArgumentException("父评论不存在"));
            parentComment.setReplyCount(parentComment.getReplyCount() + 1);
            commentRepository.save(parentComment);
        }

        return savedComment;
    }

    /**
     * 更新评论
     * 
     * @param commentId 评论 ID
     * @param content 更新后的内容
     * @param userId 操作用户 ID
     * @return 更新后的评论对象
     */
    @Transactional
    public Comment updateComment(Long commentId, String content, Long userId) {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("评论不存在"));

        // 验证权限：只能修改自己的评论
        if (!existingComment.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("无权限修改该评论");
        }

        existingComment.setContent(content);
        return commentRepository.save(existingComment);
    }

    /**
     * 删除评论
     * 
     * @param commentId 评论 ID
     * @param userId 操作用户 ID
     */
    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("评论不存在"));

        // 验证权限
        if (!existingComment.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("无权限删除该评论");
        }

        // 如果是父评论，需要减少子评论的计数
        if (existingComment.getParentId() != 0L) {
            Comment parentComment = commentRepository.findById(existingComment.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("父评论不存在"));
            parentComment.setReplyCount(Math.max(0, parentComment.getReplyCount() - 1));
            commentRepository.save(parentComment);
        }

        // 设置状态为已删除（软删除）
        existingComment.setStatus(0);
        commentRepository.save(existingComment);
    }

    /**
     * 点赞评论
     * 
     * @param commentId 评论 ID
     */
    @Transactional
    public void likeComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("评论不存在"));
        comment.setLikeCount(comment.getLikeCount() + 1);
        commentRepository.save(comment);
    }

    /**
     * 取消点赞评论
     * 
     * @param commentId 评论 ID
     */
    @Transactional
    public void unlikeComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("评论不存在"));
        comment.setLikeCount(Math.max(0, comment.getLikeCount() - 1));
        commentRepository.save(comment);
    }
}
