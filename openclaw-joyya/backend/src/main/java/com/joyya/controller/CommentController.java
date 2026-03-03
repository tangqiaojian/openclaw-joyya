package com.joyya.controller;

import com.joyya.entity.Comment;
import com.joyya.entity.Video;
import com.joyya.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 评论控制器
 * 提供评论相关的 RESTful API
 * 
 * @author Joyya
 * @version 1.0.0
 * @since 2026-03-03
 */
@RestController
@RequestMapping("/api/videos/{videoId}/comments")
@CrossOrigin(origins = "*")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 获取视频的一级评论列表
     * 
     * @param videoId 视频 ID
     * @param page 页码
     * @param size 每页数量
     * @return 一级评论分页列表
     */
    @GetMapping
    public ResponseEntity<Page<Comment>> getTopLevelComments(
            @PathVariable Long videoId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (size > 50) {
            size = 50;
        }
        
        Page<Comment> comments = commentService.getTopLevelComments(videoId, page, size);
        return ResponseEntity.ok(comments);
    }

    /**
     * 获取视频的所有评论列表（包含回复）
     * 
     * @param videoId 视频 ID
     * @param page 页码
     * @param size 每页数量
     * @return 评论分页列表
     */
    @GetMapping("/all")
    public ResponseEntity<Page<Comment>> getAllComments(
            @PathVariable Long videoId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (size > 50) {
            size = 50;
        }
        
        Page<Comment> comments = commentService.getComments(videoId, page, size);
        return ResponseEntity.ok(comments);
    }

    /**
     * 获取评论的回复列表
     * 
     * @param videoId 视频 ID
     * @param parentCommentId 父评论 ID
     * @return 回复评论列表
     */
    @GetMapping("/parent/{parentCommentId}/replies")
    public ResponseEntity<List<Comment>> getReplies(
            @PathVariable Long videoId,
            @PathVariable Long parentCommentId) {

        List<Comment> replies = commentService.getReplies(parentCommentId);
        return ResponseEntity.ok(replies);
    }

    /**
     * 统计视频的评论数量
     * 
     * @param videoId 视频 ID
     * @return 评论数量
     */
    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> countComments(@PathVariable Long videoId) {
        long count = commentService.countComments(videoId);
        return ResponseEntity.ok(Map.of("count", count));
    }

    /**
     * 发表评论
     * 
     * @param videoId 视频 ID
     * @param commentData 评论数据
     * @return 创建的评论对象
     */
    @PostMapping
    public ResponseEntity<Comment> createComment(
            @PathVariable Long videoId,
            @Valid @RequestBody Map<String, Object> commentData) {

        try {
            Long userId = Long.parseLong(commentData.get("userId").toString());
            String content = (String) commentData.get("content");
            Long parentId = commentData.get("parentId") != null ? 
                Long.parseLong(commentData.get("parentId").toString()) : 0L;

            Comment comment = commentService.createComment(videoId, userId, content, parentId);
            return ResponseEntity.status(HttpStatus.CREATED).body(comment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 更新评论
     * 
     * @param commentId 评论 ID
     * @param content 更新后的内容
     * @return 更新后的评论
     */
    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(
            @PathVariable Long commentId,
            @RequestParam Long userId,
            @RequestBody Map<String, String> data) {

        try {
            String content = data.get("content");
            Comment updatedComment = commentService.updateComment(commentId, content, userId);
            return ResponseEntity.ok(updatedComment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 删除评论
     * 
     * @param commentId 评论 ID
     * @param userId 操作用户 ID
     * @return 删除结果
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Map<String, String>> deleteComment(
            @PathVariable Long commentId,
            @RequestParam Long userId) {

        try {
            commentService.deleteComment(commentId, userId);
            return ResponseEntity.ok(Map.of("message", "评论删除成功"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 点赞评论
     * 
     * @param videoId 视频 ID
     * @param commentId 评论 ID
     * @return 点赞结果
     */
    @PostMapping("/{commentId}/like")
    public ResponseEntity<Comment> likeComment(
            @PathVariable Long videoId,
            @PathVariable Long commentId) {

        try {
            commentService.likeComment(commentId);
            return commentService.getCommentById(commentId)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 取消点赞评论
     * 
     * @param videoId 视频 ID
     * @param commentId 评论 ID
     * @return 取消点赞结果
     */
    @DeleteMapping("/{commentId}/like")
    public ResponseEntity<Comment> unlikeComment(
            @PathVariable Long videoId,
            @PathVariable Long commentId) {

        try {
            commentService.unlikeComment(commentId);
            return commentService.getCommentById(commentId)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
