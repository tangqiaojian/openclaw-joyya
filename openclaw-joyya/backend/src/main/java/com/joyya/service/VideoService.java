package com.joyya.service;

import com.joyya.entity.Category;
import com.joyya.entity.Video;
import com.joyya.repository.CategoryRepository;
import com.joyya.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 视频服务类
 * 处理视频相关的业务逻辑
 * 
 * @author Joyya
 * @version 1.0.0
 * @since 2026-03-03
 */
@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * 根据 ID 获取视频
     * 
     * @param videoId 视频 ID
     * @return 视频对象（如果存在）
     */
    @Transactional(readOnly = true)
    public Optional<Video> getVideoById(Long videoId) {
        return videoRepository.findById(videoId);
    }

    /**
     * 获取视频列表（支持分页、搜索、分类筛选）
     * 
     * @param categoryId 分类 ID（可选，null 表示不筛选）
     * @param keyword 搜索关键词（可选）
     * @param page 页码（0-based）
     * @param size 每页数量
     * @return 视频分页列表
     */
    @Transactional(readOnly = true)
    public Page<Video> getVideos(Long categoryId, String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return videoRepository.searchVideos(categoryId, keyword, pageable);
    }

    /**
     * 获取热门视频列表
     * 
     * @param page 页码
     * @param size 每页数量
     * @return 热门视频分页列表
     */
    @Transactional(readOnly = true)
    public Page<Video> getHotVideos(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "likeCount"));
        return videoRepository.findHotVideos(pageable);
    }

    /**
     * 获取最新视频列表
     * 
     * @param page 页码
     * @param size 每页数量
     * @return 最新视频分页列表
     */
    @Transactional(readOnly = true)
    public Page<Video> getLatestVideos(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return videoRepository.findLatestVideos(pageable);
    }

    /**
     * 根据分类 ID 获取视频列表
     * 
     * @param categoryId 分类 ID
     * @return 分类下的视频列表
     */
    @Transactional(readOnly = true)
    public Page<Video> getVideosByCategory(Long categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return videoRepository.searchVideos(categoryId, null, pageable);
    }

    /**
     * 根据关键词搜索视频
     * 
     * @param keyword 搜索关键词
     * @param page 页码
     * @param size 每页数量
     * @return 搜索结果的视频列表
     */
    @Transactional(readOnly = true)
    public Page<Video> searchVideos(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return videoRepository.searchVideos(null, keyword, pageable);
    }

    /**
     * 保存视频
     * 
     * @param video 视频对象
     * @return 保存后的视频对象
     */
    @Transactional
    public Video saveVideo(Video video) {
        // 验证分类是否存在
        if (video.getCategory() != null) {
            Optional<Category> categoryOpt = categoryRepository.findById(video.getCategory().getId());
            if (!categoryOpt.isPresent()) {
                throw new IllegalArgumentException("分类不存在");
            }
        }
        return videoRepository.save(video);
    }

    /**
     * 更新视频信息
     * 
     * @param videoId 视频 ID
     * @param video 更新后的视频对象
     * @return 更新后的视频对象
     */
    @Transactional
    public Video updateVideo(Long videoId, Video video) {
        Video existingVideo = videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("视频不存在"));
        
        // 更新字段
        existingVideo.setTitle(video.getTitle());
        existingVideo.setDescription(video.getDescription());
        existingVideo.setCover(video.getCover());
        existingVideo.setVideoUrl(video.getVideoUrl());
        existingVideo.setDuration(video.getDuration());
        if (video.getCategory() != null) {
            existingVideo.setCategory(video.getCategory());
        }
        existingVideo.setStatus(video.getStatus());
        existingVideo.setIsRecommend(video.getIsRecommend());
        existingVideo.setIsHot(video.getIsHot());
        
        return videoRepository.save(existingVideo);
    }

    /**
     * 删除视频
     * 
     * @param videoId 视频 ID
     */
    @Transactional
    public void deleteVideo(Long videoId) {
        if (!videoRepository.existsById(videoId)) {
            throw new IllegalArgumentException("视频不存在");
        }
        videoRepository.deleteById(videoId);
    }

    /**
     * 增加播放量
     * 
     * @param videoId 视频 ID
     */
    @Transactional
    public void incrementViewCount(Long videoId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("视频不存在"));
        video.setViewCount(video.getViewCount() + 1);
        videoRepository.save(video);
    }

    /**
     * 点赞视频
     * 
     * @param videoId 视频 ID
     */
    @Transactional
    public void incrementLikeCount(Long videoId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("视频不存在"));
        video.setLikeCount(video.getLikeCount() + 1);
        videoRepository.save(video);
    }

    /**
     * 分享视频
     * 
     * @param videoId 视频 ID
     */
    @Transactional
    public void incrementShareCount(Long videoId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("视频不存在"));
        video.setShareCount(video.getShareCount() + 1);
        videoRepository.save(video);
    }
}
