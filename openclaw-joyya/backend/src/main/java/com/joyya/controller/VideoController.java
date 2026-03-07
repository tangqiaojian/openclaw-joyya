package com.joyya.controller;

import com.joyya.entity.Video;
import com.joyya.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Map;
import java.util.Optional;

/**
 * 视频控制器
 * 提供视频相关的 RESTful API
 * 
 * @author Joyya
 * @version 1.0.0
 * @since 2026-03-03
 */
@RestController
@RequestMapping("/api/videos")
@CrossOrigin(origins = "*")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping
    public ResponseEntity<Page<Video>> getVideos(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (size > 50) size = 50;
        Page<Video> videos = videoService.getVideos(categoryId, keyword, page, size);
        return ResponseEntity.ok(videos);
    }

    @GetMapping("/{videoId}")
    public ResponseEntity<Video> getVideoById(@PathVariable Long videoId) {
        Optional<Video> video = videoService.getVideoById(videoId);
        return video.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/hot")
    public ResponseEntity<Page<Video>> getHotVideos(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        if (size > 50) size = 50;
        Page<Video> videos = videoService.getHotVideos(page, size);
        return ResponseEntity.ok(videos);
    }

    @GetMapping("/latest")
    public ResponseEntity<Page<Video>> getLatestVideos(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        if (size > 50) size = 50;
        Page<Video> videos = videoService.getLatestVideos(page, size);
        return ResponseEntity.ok(videos);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<Video>> getVideosByCategory(@PathVariable Long categoryId,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        if (size > 50) size = 50;
        Page<Video> videos = videoService.getVideosByCategory(categoryId, page, size);
        return ResponseEntity.ok(videos);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Video>> searchVideos(@RequestParam String keyword,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        if (size > 50) size = 50;
        Page<Video> videos = videoService.searchVideos(keyword, page, size);
        return ResponseEntity.ok(videos);
    }

    @PostMapping
    public ResponseEntity<Video> saveVideo(@Valid @RequestBody Video video) {
        Video savedVideo = videoService.saveVideo(video);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVideo);
    }

    @PutMapping("/{videoId}")
    public ResponseEntity<Video> updateVideo(@PathVariable Long videoId, @Valid @RequestBody Video video) {
        try {
            Video updatedVideo = videoService.updateVideo(videoId, video);
            return ResponseEntity.ok(updatedVideo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{videoId}")
    public ResponseEntity<Map<String, String>> deleteVideo(@PathVariable Long videoId) {
        try {
            videoService.deleteVideo(videoId);
            return ResponseEntity.ok(Map.of("message", "视频删除成功"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{videoId}/view")
    public ResponseEntity<Video> incrementViewCount(@PathVariable Long videoId) {
        try {
            videoService.incrementViewCount(videoId);
            return videoService.getVideoById(videoId).map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{videoId}/like")
    public ResponseEntity<Video> incrementLikeCount(@PathVariable Long videoId) {
        try {
            videoService.incrementLikeCount(videoId);
            return videoService.getVideoById(videoId).map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{videoId}/share")
    public ResponseEntity<Video> incrementShareCount(@PathVariable Long videoId) {
        try {
            videoService.incrementShareCount(videoId);
            return videoService.getVideoById(videoId).map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
