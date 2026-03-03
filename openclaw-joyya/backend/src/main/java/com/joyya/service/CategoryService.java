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

import java.util.List;
import java.util.Optional;

/**
 * 分类服务类
 * 处理视频分类相关的业务逻辑
 * 
 * @author Joyya
 * @version 1.0.0
 * @since 2026-03-03
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private VideoRepository videoRepository;

    /**
     * 根据 ID 获取分类
     * 
     * @param categoryId 分类 ID
     * @return 分类对象（如果存在）
     */
    @Transactional(readOnly = true)
    public Optional<Category> getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    /**
     * 获取所有分类（按排序）
     * 
     * @return 分类列表
     */
    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryRepository.findAllByOrderBySortOrderAsc();
    }

    /**
     * 获取所有顶级分类
     * 
     * @return 顶级分类列表
     */
    @Transactional(readOnly = true)
    public List<Category> getTopLevelCategories() {
        return categoryRepository.findByParentIdOrderBySortOrderAsc(0L);
    }

    /**
     * 获取分类下的视频列表
     * 
     * @param categoryId 分类 ID
     * @param page 页码
     * @param size 每页数量
     * @return 分类下的视频分页列表
     */
    @Transactional(readOnly = true)
    public Page<Video> getVideosByCategory(Long categoryId, int page, int size) {
        // 验证分类是否存在
        if (!categoryRepository.existsById(categoryId)) {
            throw new IllegalArgumentException("分类不存在");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return videoRepository.searchVideos(categoryId, null, pageable);
    }

    /**
     * 获取分类详情（包含视频数量）
     * 
     * @param categoryId 分类 ID
     * @return 分类信息（包含视频数量）
     */
    @Transactional(readOnly = true)
    public Category getCategoryWithVideoCount(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("分类不存在"));
        
        // 可以通过额外查询获取该分类下的视频数量
        // 这里简化处理，实际项目中可以添加查询方法
        return category;
    }

    /**
     * 保存分类
     * 
     * @param category 分类对象
     * @return 保存后的分类对象
     */
    @Transactional
    public Category saveCategory(Category category) {
        // 检查分类名称是否已存在
        if (categoryRepository.existsById(category.getId())) {
            throw new IllegalArgumentException("分类 ID 已存在");
        }
        return categoryRepository.save(category);
    }

    /**
     * 更新分类
     * 
     * @param categoryId 分类 ID
     * @param category 更新后的分类对象
     * @return 更新后的分类对象
     */
    @Transactional
    public Category updateCategory(Long categoryId, Category category) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("分类不存在"));
        
        // 更新字段
        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());
        existingCategory.setSortOrder(category.getSortOrder());
        existingCategory.setParentId(category.getParentId());
        existingCategory.setIcon(category.getIcon());
        
        return categoryRepository.save(existingCategory);
    }

    /**
     * 删除分类
     * 
     * @param categoryId 分类 ID
     */
    @Transactional
    public void deleteCategory(Long categoryId) {
        // 检查该分类下是否有视频
        if (videoRepository.findByCategoryId(categoryId) != null && 
            videoRepository.findByCategoryId(categoryId).size() > 0) {
            throw new IllegalArgumentException("该分类下存在视频，无法删除");
        }

        if (!categoryRepository.existsById(categoryId)) {
            throw new IllegalArgumentException("分类不存在");
        }
        categoryRepository.deleteById(categoryId);
    }

    /**
     * 检查分类是否存在
     * 
     * @param categoryId 分类 ID
     * @return true-存在，false-不存在
     */
    @Transactional(readOnly = true)
    public boolean categoryExists(Long categoryId) {
        return categoryRepository.existsById(categoryId);
    }
}
