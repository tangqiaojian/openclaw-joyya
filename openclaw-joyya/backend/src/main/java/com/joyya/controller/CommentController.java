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

@RestController
@RequestMapping("/api/videos/{videoId}/comments")
@CrossOrigin(origins = "*")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<Page<Comment>> getTopLevelComments(@PathVariable Long videoId,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        if (size > 50) size = 50;
        Page<Comment> comments = commentService.getTopLevelComments(videoId, page, size);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Comment>> getAllComments(@PathVariable Long videoId,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        if (size > 50) size = 50;
        Page<Comment> comments = commentService.getComments(videoId, page, size);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/parent/{parentCommentId}/replies")
    public ResponseEntity<List<Comment>> getReplies(@PathVariable Long videoId,
                                                      @PathVariable Long parentCommentId) {
        return ResponseEntity.ok(commentService.getReplies(parentCommentId));
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> countComments(@PathVariable Long videoId) {
        return ResponseEntity.ok(Map.of("count", commentService.countComments(videoId)));
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@PathVariable Long videoId,
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

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long commentId,
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

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Map<String, String>> deleteComment(@PathVariable Long commentId,
                                                               @RequestParam Long userId) {
        try {
            commentService.deleteComment(commentId, userId);
            return ResponseEntity.ok(Map.of("message", "评论删除成功"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{commentId}/like")
    public ResponseEntity<Comment> likeComment(@PathVariable Long videoId,
                                               @PathVariable Long commentId) {
        try {
            commentService.likeComment(commentId);
            return commentService.getCommentById(commentId).map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{commentId}/like")
    public ResponseEntity<Comment> unlikeComment(@PathVariable Long videoId,
                                                  @PathVariable Long commentId) {
        try {
            commentService.unlikeComment(commentId);
            return commentService.getCommentById(commentId).map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
