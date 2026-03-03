package com.joyya.controller;

import com.joyya.entity.Category;
import com.joyya.entity.Video;
import com.joyya.service.CategoryService;
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
 * 分类控制器
 * 提供视频分类相关的 RESTful API
 * 
 * @author Joyya
 * @version 1.0.0
 * @since 2026-03-03
 */
@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取所有分类列表（按排序）
     * 
     * @return 分类列表
     */
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    /**
     * 根据 ID 获取分类
     * 
     * @param categoryId 分类 ID
     * @return 分类详情
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long categoryId) {
        Optional<Category> category = categoryService.getCategoryById(categoryId);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * 获取所有顶级分类
     * 
     * @return 顶级分类列表
     */
    @GetMapping("/top")
    public ResponseEntity<List<Category>> getTopLevelCategories() {
        List<Category> categories = categoryService.getTopLevelCategories();
        return ResponseEntity.ok(categories);
    }

    /**
     * 获取分类下的视频列表
     * 
     * @param categoryId 分类 ID
     * @param page 页码
     * @param size 每页数量
     * @return 分类下的视频列表
     */
    @GetMapping("/{categoryId}/videos")
    public ResponseEntity<Page<Video>> getVideosByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (size > 50) {
            size = 50;
        }
        
        Page<Video> videos = categoryService.getVideosByCategory(categoryId, page, size);
        return ResponseEntity.ok(videos);
    }

    /**
     * 保存分类
     * 
     * @param category 分类对象
     * @return 保存后的分类
     */
    @PostMapping
    public ResponseEntity<Category> saveCategory(@Valid @RequestBody Category category) {
        Category savedCategory = categoryService.saveCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    /**
     * 更新分类信息
     * 
     * @param categoryId 分类 ID
     * @param category 更新后的分类对象
     * @return 更新后的分类
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable Long categoryId,
            @Valid @RequestBody Category category) {

        try {
            Category updatedCategory = categoryService.updateCategory(categoryId, category);
            return ResponseEntity.ok(updatedCategory);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 删除分类
     * 
     * @param categoryId 分类 ID
     * @return 删除结果
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Map<String, String>> deleteCategory(@PathVariable Long categoryId) {
        try {
            categoryService.deleteCategory(categoryId);
            return ResponseEntity.ok(Map.of("message", "分类删除成功"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 检查分类是否存在
     * 
     * @param categoryId 分类 ID
     * @return 检查结果
     */
    @GetMapping("/{categoryId}/exists")
    public ResponseEntity<Map<String, Boolean> categoryExists(@PathVariable Long categoryId) {
        boolean exists = categoryService.categoryExists(categoryId);
        return ResponseEntity.ok(Map.of("exists", exists));
    }
}
