package com.joyya.service;

import com.joyya.entity.Tag;
import com.joyya.entity.Video;
import com.joyya.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 标签服务类
 * 处理标签相关的业务逻辑
 * 
 * @author Joyya
 * @version 1.0.0
 * @since 2026-03-03
 */
@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    /**
     * 根据 ID 获取标签
     * 
     * @param tagId 标签 ID
     * @return 标签对象（如果存在）
     */
    @Transactional(readOnly = true)
    public Optional<Tag> getTagById(Long tagId) {
        return tagRepository.findById(tagId);
    }

    /**
     * 根据名称获取标签
     * 
     * @param name 标签名称
     * @return 标签对象（如果存在）
     */
    @Transactional(readOnly = true)
    public Optional<Tag> getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    /**
     * 获取所有标签
     * 
     * @return 标签列表
     */
    @Transactional(readOnly = true)
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    /**
     * 获取所有标签名称列表
     * 
     * @return 标签名称列表
     */
    @Transactional(readOnly = true)
    public List<String> getTagNames() {
        return tagRepository.findAll().stream()
                .map(Tag::getName)
                .collect(Collectors.toList());
    }

    /**
     * 根据关键词搜索标签
     * 
     * @param keyword 搜索关键词
     * @return 匹配的标签列表
     */
    @Transactional(readOnly = true)
    public List<Tag> searchTags(String keyword) {
        return tagRepository.findByNameContaining(keyword);
    }

    /**
     * 根据标签名称或 ID 获取标签
     * 
     * @param tagIdentifier 标签名称或 ID
     * @return 标签对象（如果存在）
     */
    @Transactional(readOnly = true)
    public Optional<Tag> findTagByIdentifier(String tagIdentifier) {
        try {
            // 尝试作为 ID 解析
            Long id = Long.parseLong(tagIdentifier);
            return tagRepository.findById(id);
        } catch (NumberFormatException e) {
            // 作为名称搜索
            return tagRepository.findByName(tagIdentifier);
        }
    }

    /**
     * 保存标签
     * 
     * @param tag 标签对象
     * @return 保存后的标签对象
     */
    @Transactional
    public Tag saveTag(Tag tag) {
        // 检查标签名称是否已存在
        Optional<Tag> existingTag = tagRepository.findByName(tag.getName());
        if (existingTag.isPresent()) {
            throw new IllegalArgumentException("标签名称已存在");
        }
        return tagRepository.save(tag);
    }

    /**
     * 更新标签
     * 
     * @param tagId 标签 ID
     * @param tag 更新后的标签对象
     * @return 更新后的标签对象
     */
    @Transactional
    public Tag updateTag(Long tagId, Tag tag) {
        Tag existingTag = tagRepository.findById(tagId)
                .orElseThrow(() -> new IllegalArgumentException("标签不存在"));
        
        // 更新字段
        existingTag.setName(tag.getName());
        existingTag.setDescription(tag.getDescription());
        existingTag.setSortOrder(tag.getSortOrder());
        
        return tagRepository.save(existingTag);
    }

    /**
     * 删除标签
     * 
     * @param tagId 标签 ID
     */
    @Transactional
    public void deleteTag(Long tagId) {
        if (!tagRepository.existsById(tagId)) {
            throw new IllegalArgumentException("标签不存在");
        }
        tagRepository.deleteById(tagId);
    }

    /**
     * 根据标签名称获取或创建标签
     * 
     * @param name 标签名称
     * @return 标签对象
     */
    @Transactional
    public Tag getOrCreateTag(String name) {
        Optional<Tag> existingTag = tagRepository.findByName(name);
        if (existingTag.isPresent()) {
            return existingTag.get();
        }

        Tag newTag = new Tag();
        newTag.setName(name);
        newTag.setSortOrder(0);
        return tagRepository.save(newTag);
    }
}
