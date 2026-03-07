package com.joyya.service;

import com.joyya.entity.Collection;
import com.joyya.entity.User;
import com.joyya.entity.Video;
import com.joyya.entity.VideoLike;
import com.joyya.repository.CollectionRepository;
import com.joyya.repository.UserRepository;
import com.joyya.repository.VideoLikeRepository;
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
 * 互动服务类
 * 处理用户互动相关的业务逻辑（点赞、收藏、关注、观看历史）
 * 
 * @author Joyya
 * @version 1.0.0
 * @since 2026-03-03
 */
@Service
public class InteractionService {

    @Autowired
    private VideoLikeRepository videoLikeRepository;

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoRepository videoRepository;

    /**
     * 点赞视频
     * 
     * @param videoId 视频 ID
     * @param userId 用户 ID
     * @return true-点赞成功，false-用户未登录或视频不存在
     */
    @Transactional
    public boolean likeVideo(Long videoId, Long userId) {
        // 验证用户和视频是否存在
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("用户不存在");
        }
        if (!videoRepository.existsById(videoId)) {
            throw new IllegalArgumentException("视频不存在");
        }

        // 检查是否已点赞
        Optional<VideoLike> existingLike = videoLikeRepository.findByVideoIdAndUserId(videoId, userId);
        if (existingLike.isPresent()) {
            return false; // 已点赞
        }

        VideoLike videoLike = new VideoLike();
        videoLike.setVideoId(videoId);
        videoLike.setUserId(userId);
        videoLikeRepository.save(videoLike);

        // 增加视频点赞数
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("视频不存在"));
        video.setLikeCount(video.getLikeCount() + 1);
        videoRepository.save(video);

        return true;
    }

    /**
     * 取消点赞视频
     * 
     * @param videoId 视频 ID
     * @param userId 用户 ID
     * @return true-取消点赞成功，false-用户未点赞
     */
    @Transactional
    public boolean unlikeVideo(Long videoId, Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("用户不存在");
        }
        if (!videoRepository.existsById(videoId)) {
            throw new IllegalArgumentException("视频不存在");
        }

        // 查找点赞记录
        Optional<VideoLike> likeOpt = videoLikeRepository.findByVideoIdAndUserId(videoId, userId);
        if (likeOpt.isEmpty()) {
            return false; // 未点赞
        }

        videoLikeRepository.deleteById(likeOpt.get().getId());

        // 减少视频点赞数
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("视频不存在"));
        video.setLikeCount(Math.max(0, video.getLikeCount() - 1));
        videoRepository.save(video);

        return true;
    }

    /**
     * 检查用户是否已点赞视频
     * 
     * @param videoId 视频 ID
     * @param userId 用户 ID
     * @return true-已点赞，false-未点赞
     */
    @Transactional(readOnly = true)
    public boolean hasLiked(Long videoId, Long userId) {
        return videoLikeRepository.findByVideoIdAndUserId(videoId, userId).isPresent();
    }

    /**
     * 收藏视频
     * 
     * @param videoId 视频 ID
     * @param userId 用户 ID
     * @return true-收藏成功，false-已收藏
     */
    @Transactional
    public boolean collectVideo(Long videoId, Long userId) {
        // 验证用户和视频是否存在
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("用户不存在");
        }
        if (!videoRepository.existsById(videoId)) {
            throw new IllegalArgumentException("视频不存在");
        }

        // 检查是否已收藏
        Optional<Collection> existingCollection = collectionRepository.findByUserIdAndVideoId(userId, videoId);
        if (existingCollection.isPresent()) {
            return false; // 已收藏
        }

        Collection collection = new Collection();
        collection.setUserId(userId);
        collection.setVideoId(videoId);
        collectionRepository.save(collection);

        // 增加视频收藏数
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("视频不存在"));
        video.setCoinCount(video.getCoinCount() + 1);
        videoRepository.save(video);

        return true;
    }

    /**
     * 取消收藏视频
     * 
     * @param videoId 视频 ID
     * @param userId 用户 ID
     * @return true-取消收藏成功，false-未收藏
     */
    @Transactional
    public boolean uncollectVideo(Long videoId, Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("用户不存在");
        }
        if (!videoRepository.existsById(videoId)) {
            throw new IllegalArgumentException("视频不存在");
        }

        // 查找收藏记录
        Optional<Collection> collectionOpt = collectionRepository.findByUserIdAndVideoId(userId, videoId);
        if (collectionOpt.isEmpty()) {
            return false; // 未收藏
        }

        collectionRepository.deleteById(collectionOpt.get().getId());

        // 减少视频收藏数
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("视频不存在"));
        video.setCoinCount(Math.max(0, video.getCoinCount() - 1));
        videoRepository.save(video);

        return true;
    }

    /**
     * 检查用户是否已收藏视频
     * 
     * @param videoId 视频 ID
     * @param userId 用户 ID
     * @return true-已收藏，false-未收藏
     */
    @Transactional(readOnly = true)
    public boolean hasCollected(Long videoId, Long userId) {
        return collectionRepository.findByUserIdAndVideoId(userId, videoId).isPresent();
    }

    /**
     * 获取用户收藏的视频列表
     * 
     * @param userId 用户 ID
     * @param page 页码
     * @param size 每页数量
     * @return 收藏的视频分页列表
     */
    @Transactional(readOnly = true)
    public Page<Video> getUserCollections(Long userId, int page, int size) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("用户不存在");
        }

        Page<Collection> collections = collectionRepository.findByUserIdOrderByCreatedAtDesc(userId, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt")));
        
        // 手动将 Collection 转换为 Video
        return collections.getContent().stream()
                .map(collection -> videoRepository.findById(collection.getVideoId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(java.util.stream.Collectors.collectingAndThen(
                        java.util.stream.Collectors.toList(),
                        list -> new org.springframework.data.domain.PageImpl<>(list, collections.getPageable(), collections.getTotalElements())
                ));
    }

    /**
     * 关注用户
     * 
     * @param followerId 关注者 ID
     * @param followeeId 被关注者 ID
     * @return true-关注成功，false-已关注
     */
    @Transactional
    public boolean followUser(Long followerId, Long followeeId) {
        // 验证用户是否存在
        if (!userRepository.existsById(followerId) || !userRepository.existsById(followeeId)) {
            throw new IllegalArgumentException("用户不存在");
        }

        // 检查是否已关注
        // TODO: 需要 FollowRepository，这里简化处理
        return false;
    }

    /**
     * 取消关注用户
     * 
     * @param followerId 关注者 ID
     * @param followeeId 被关注者 ID
     * @return true-取消关注成功，false-未关注
     */
    @Transactional
    public boolean unfollowUser(Long followerId, Long followeeId) {
        // TODO: 需要 FollowRepository
        return false;
    }

    /**
     * 记录观看历史
     * 
     * @param videoId 视频 ID
     * @param userId 用户 ID
     * @param progress 观看进度（秒）
     * @return true-记录成功
     */
    @Transactional
    public boolean recordWatchHistory(Long videoId, Long userId, int progress) {
        // 验证用户和视频是否存在
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("用户不存在");
        }
        if (!videoRepository.existsById(videoId)) {
            throw new IllegalArgumentException("视频不存在");
        }

        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("视频不存在"));

        //  TODO: 需要 WatchHistoryRepository，这里简化处理
        return true;
    }
}
