package com.joyya.controller;

import com.joyya.entity.User;
import com.joyya.entity.Video;
import com.joyya.service.InteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/interactions")
@CrossOrigin(origins = "*")
public class InteractionController {

    @Autowired
    private InteractionService interactionService;

    @PostMapping("/videos/{videoId}/like")
    public ResponseEntity<Map<String, Object>> likeVideo(@PathVariable Long videoId,
                                                          @RequestParam Long userId) {
        try {
            boolean success = interactionService.likeVideo(videoId, userId);
            return ResponseEntity.ok(Map.of("success", success,
                    "message", success ? "点赞成功" : "已点赞"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/videos/{videoId}/like")
    public ResponseEntity<Map<String, Object>> unlikeVideo(@PathVariable Long videoId,
                                                            @RequestParam Long userId) {
        try {
            boolean success = interactionService.unlikeVideo(videoId, userId);
            return ResponseEntity.ok(Map.of("success", success,
                    "message", success ? "取消点赞成功" : "未点赞"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/videos/{videoId}/like/status")
    public ResponseEntity<Map<String, Boolean>> hasLiked(@PathVariable Long videoId,
                                                          @RequestParam Long userId) {
        return ResponseEntity.ok(Map.of("hasLiked", interactionService.hasLiked(videoId, userId)));
    }

    @PostMapping("/videos/{videoId}/collect")
    public ResponseEntity<Map<String, Object>> collectVideo(@PathVariable Long videoId,
                                                             @RequestParam Long userId) {
        try {
            boolean success = interactionService.collectVideo(videoId, userId);
            return success ? ResponseEntity.status(HttpStatus.CREATED).body(Map.of("success", true, "message", "收藏成功"))
                    : ResponseEntity.ok(Map.of("success", false, "message", "已收藏"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/videos/{videoId}/collect")
    public ResponseEntity<Map<String, Object>> uncollectVideo(@PathVariable Long videoId,
                                                               @RequestParam Long userId) {
        try {
            boolean success = interactionService.uncollectVideo(videoId, userId);
            return ResponseEntity.ok(Map.of("success", success,
                    "message", success ? "取消收藏成功" : "未收藏"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/videos/{videoId}/collect/status")
    public ResponseEntity<Map<String, Boolean>> hasCollected(@PathVariable Long videoId,
                                                               @RequestParam Long userId) {
        return ResponseEntity.ok(Map.of("hasCollected", interactionService.hasCollected(videoId, userId)));
    }

    @GetMapping("/users/{userId}/collections")
    public ResponseEntity<Page<Video>> getUserCollections(@PathVariable Long userId,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        if (size > 50) size = 50;
        Page<Video> videos = interactionService.getUserCollections(userId, page, size);
        return ResponseEntity.ok(videos);
    }

    @PostMapping("/users/follow")
    public ResponseEntity<Map<String, Object>> followUser(@RequestParam Long followerId,
                                                            @RequestParam Long followeeId) {
        return ResponseEntity.ok(Map.of("success", false, "message", "关注功能暂时不可用"));
    }

    @DeleteMapping("/users/unfollow")
    public ResponseEntity<Map<String, Object>> unfollowUser(@RequestParam Long followerId,
                                                              @RequestParam Long followeeId) {
        return ResponseEntity.ok(Map.of("success", false, "message", "取消关注功能暂时不可用"));
    }

    @PostMapping("/watch-history")
    public ResponseEntity<Map<String, Boolean>> recordWatchHistory(@RequestParam Long videoId,
                                                                     @RequestParam Long userId,
                                                                     @RequestParam int progress) {
        try {
            boolean success = interactionService.recordWatchHistory(videoId, userId, progress);
            return ResponseEntity.ok(Map.of("success", success));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/users/{userId}/watch-history")
    public ResponseEntity<List<Map<String, Object>>> getUserWatchHistory(@PathVariable Long userId,
                                                                            @RequestParam(defaultValue = "0") int page,
                                                                            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(List.of());
    }
}
