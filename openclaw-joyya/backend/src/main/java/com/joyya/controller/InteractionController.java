package com.joyya.controller;

import com.joyya.entity.User;
import com.joyya.entity.Video;
import com.joyya.service.InteractionService;
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
 * 互动控制器
 * 提供用户互动相关的 RESTful API（点赞、收藏、关注）
 * 
 * @author Joyya
 * @version 1.0.0
 * @since 2026-03-03
 */
@RestController
@RequestMapping("/api/interactions")
@CrossOrigin(origins = "*")
public class InteractionController {

    @Autowired
    private InteractionService interactionService;

    /**
     * 点赞视频
     * 
     * @param videoId 视频 ID
     * @param userId 用户 ID
     * @return 点赞结果
     */
    @PostMapping("/videos/{videoId}/like")
    public ResponseEntity<Map<String, Object>> likeVideo(
            @PathVariable Long videoId,
            @RequestParam Long userId) {

        try {
            boolean success = interactionService.likeVideo(videoId, userId);
            if (success) {
                return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "点赞成功"
                ));
            } else {
                return ResponseEntity.ok(Map.of(
                    "success", false,
                    "message", "已点赞"
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 取消点赞视频
     * 
     * @param videoId 视频 ID
     * @param userId 用户 ID
     * @return 取消点赞结果
     */
    @DeleteMapping("/videos/{videoId}/like")
    public ResponseEntity<Map<String, Object>> unlikeVideo(
            @PathVariable Long videoId,
            @RequestParam Long userId) {

        try {
            boolean success = interactionService.unlikeVideo(videoId, userId);
            if (success) {
                return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "取消点赞成功"
                ));
            } else {
                return ResponseEntity.ok(Map.of(
                    "success", false,
                    "message", "未点赞"
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 检查用户是否已点赞视频
     * 
     * @param videoId 视频 ID
     * @param userId 用户 ID
     * @return 检查结果
     */
    @GetMapping("/videos/{videoId}/like/status")
    public ResponseEntity<Map<String, Boolean>> hasLiked(
            @PathVariable Long videoId,
            @RequestParam Long userId) {

        boolean hasLiked = interactionService.hasLiked(videoId, userId);
        return ResponseEntity.ok(Map.of("hasLiked", hasLiked));
    }

    /**
     * 收藏视频
     * 
     * @param videoId 视频 ID
     * @param userId 用户 ID
     * @return 收藏结果
     */
    @PostMapping("/videos/{videoId}/collect")
    public ResponseEntity<Map<String, Object>> collectVideo(
            @PathVariable Long videoId,
            @RequestParam Long userId) {

        try {
            boolean success = interactionService.collectVideo(videoId, userId);
            if (success) {
                return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "success", true,
                    "message", "收藏成功"
                ));
            } else {
                return ResponseEntity.ok(Map.of(
                    "success", false,
                    "message", "已收藏"
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 取消收藏视频
     * 
     * @param videoId 视频 ID
     * @param userId 用户 ID
     * @return 取消收藏结果
     */
    @DeleteMapping("/videos/{videoId}/collect")
    public ResponseEntity<Map<String, Object>> uncollectVideo(
            @PathVariable Long videoId,
            @RequestParam Long userId) {

        try {
            boolean success = interactionService.uncollectVideo(videoId, userId);
            if (success) {
                return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "取消收藏成功"
                ));
            } else {
                return ResponseEntity.ok(Map.of(
                    "success", false,
                    "message", "未收藏"
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 检查用户是否已收藏视频
     * 
     * @param videoId 视频 ID
     * @param userId 用户 ID
     * @return 检查结果
     */
    @GetMapping("/videos/{videoId}/collect/status")
    public ResponseEntity<Map<String, Boolean>> hasCollected(
            @PathVariable Long videoId,
            @RequestParam Long userId) {

        boolean hasCollected = interactionService.hasCollected(videoId, userId);
        return ResponseEntity.ok(Map.of("hasCollected", hasCollected));
    }

    /**
     * 获取用户收藏的视频列表
     * 
     * @param userId 用户 ID
     * @param page 页码
     * @param size 每页数量
     * @return 收藏的视频分页列表
     */
    @GetMapping("/users/{userId}/collections")
    public ResponseEntity<Page<Video>> getUserCollections(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (size > 50) {
            size = 50;
        }
        
        Page<Video> videos = interactionService.getUserCollections(userId, page, size);
        return ResponseEntity.ok(videos);
    }

    /**
     * 关注用户
     * 
     * @param followerId 关注者 ID
     * @param followeeId 被关注者 ID
     * @return 关注结果
     */
    @PostMapping("/users/follow")
    public ResponseEntity<Map<String, Object>> followUser(
            @RequestParam Long followerId,
            @RequestParam Long followeeId) {

        // TODO: FollowRepository 未实现，暂时返回不支持
        return ResponseEntity.ok(Map.of(
            "success", false,
            "message", "关注功能暂时不可用"
        ));
    }

    /**
     * 取消关注用户
     * 
     * @param followerId 关注者 ID
     * @param followeeId 被关注者 ID
     * @return 取消关注结果
     */
    @DeleteMapping("/users/unfollow")
    public ResponseEntity<Map<String, Object>> unfollowUser(
            @RequestParam Long followerId,
            @RequestParam Long followeeId) {

        // TODO: FollowRepository 未实现，暂时返回不支持
        return ResponseEntity.ok(Map.of(
            "success", false,
            "message", "取消关注功能暂时不可用"
        ));
    }

    /**
     * 记录观看历史
     * 
     * @param videoId 视频 ID
     * @param userId 用户 ID
     * @param progress 观看进度（秒）
     * @return 记录结果
     */
    @PostMapping("/watch-history")
    public ResponseEntity<Map<String, Boolean>> recordWatchHistory(
            @RequestParam Long videoId,
            @RequestParam Long userId,
            @RequestParam int progress) {

        try {
            boolean success = interactionService.recordWatchHistory(videoId, userId, progress);
            return ResponseEntity.ok(Map.of("success", success));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 获取用户观看历史
     * 
     * @param userId 用户 ID
     * @param page 页码
     * @param size 每页数量
     * @return 观看历史分页列表
     */
    @GetMapping("/users/{userId}/watch-history")
    public ResponseEntity<List<Map<String, Object>>> getUserWatchHistory(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        // TODO: WatchHistoryRepository 未实现，暂时返回空列表
        return ResponseEntity.ok(List.of());
    }
}
