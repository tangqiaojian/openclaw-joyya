package com.joyya.repository;

import com.joyya.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 标签数据访问层（Repository）
 * 提供标签数据的 CRUD 操作和自定义查询方法
 * 
 * @author Joyya
 * @version 1.0.0
 * @since 2026-03-03
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    /**
     * 根据名称查询标签
     * 
     * @param name 标签名称
     * @return 标签对象（如果存在）
     */
    Optional<Tag> findByName(String name);

    /**
     * 根据名称模糊查询标签
     * 
     * @param name 标签名称关键词
     * @return 匹配的标签列表
     */
    List<Tag> findByNameContaining(String name);
}
