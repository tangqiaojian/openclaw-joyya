package com.joyya.repository;

import com.joyya.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 分类数据访问层（Repository）
 * 提供视频分类数据的 CRUD 操作和自定义查询方法
 * 
 * @author Joyya
 * @version 1.0.0
 * @since 2026-03-03
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * 查询所有分类（按排序）
     * 
     * @return 分类列表
     */
    List<Category> findAllByOrderBySortOrderAsc();

    /**
     * 查询顶级分类
     * 
     * @return 顶级分类列表
     */
    List<Category> findByParentIdOrderBySortOrderAsc(Long parentId);
}
