package com.joyya.controller;

import com.joyya.entity.Tag;
import com.joyya.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 标签控制器
 * 提供标签相关的 RESTful API
 * 
 * @author Joyya
 * @version 1.0.0
 * @since 2026-03-03
 */
@RestController
@RequestMapping("/api/tags")
@CrossOrigin(origins = "*")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 获取所有标签列表
     * 
     * @return 标签列表
     */
    @GetMapping
    public ResponseEntity<List<Tag>> getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        return ResponseEntity.ok(tags);
    }

    /**
     * 根据 ID 获取标签
     * 
     * @param tagId 标签 ID
     * @return 标签详情
     */
    @GetMapping("/{tagId}")
    public ResponseEntity<Tag> getTagById(@PathVariable Long tagId) {
        Optional<Tag> tag = tagService.getTagById(tagId);
        return tag.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * 根据名称获取标签
     * 
     * @param name 标签名称
     * @return 标签详情
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<Tag> getTagByName(@PathVariable String name) {
        Optional<Tag> tag = tagService.getTagByName(name);
        return tag.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * 根据关键词搜索标签
     * 
     * @param keyword 搜索关键词
     * @return 匹配的标签列表
     */
    @GetMapping("/search")
    public ResponseEntity<List<Tag>> searchTags(@RequestParam String keyword) {
        List<Tag> tags = tagService.searchTags(keyword);
        return ResponseEntity.ok(tags);
    }

    /**
     * 根据标识符（名称或 ID）获取标签
     * 
     * @param identifier 标签名称或 ID
     * @return 标签详情
     */
    @GetMapping("/identifier/{identifier}")
    public ResponseEntity<Tag> getTagByIdentifier(@PathVariable String identifier) {
        Optional<Tag> tag = tagService.findTagByIdentifier(identifier);
        return tag.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * 保存标签
     * 
     * @param tag 标签对象
     * @return 保存后的标签
     */
    @PostMapping
    public ResponseEntity<Tag> saveTag(@Valid @RequestBody Tag tag) {
        try {
            Tag savedTag = tagService.saveTag(tag);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTag);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 更新标签信息
     * 
     * @param tagId 标签 ID
     * @param tag 更新后的标签对象
     * @return 更新后的标签
     */
    @PutMapping("/{tagId}")
    public ResponseEntity<Tag> updateTag(
            @PathVariable Long tagId,
            @Valid @RequestBody Tag tag) {

        try {
            Tag updatedTag = tagService.updateTag(tagId, tag);
            return ResponseEntity.ok(updatedTag);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 删除标签
     * 
     * @param tagId 标签 ID
     * @return 删除结果
     */
    @DeleteMapping("/{tagId}")
    public ResponseEntity<Map<String, String>> deleteTag(@PathVariable Long tagId) {
        try {
            tagService.deleteTag(tagId);
            return ResponseEntity.ok(Map.of("message", "标签删除成功"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 获取所有标签名称列表
     * 
     * @return 标签名称列表
     */
    @GetMapping("/names")
    public ResponseEntity<List<String>> getTagNames() {
        List<String> names = tagService.getTagNames();
        return ResponseEntity.ok(names);
    }
}
