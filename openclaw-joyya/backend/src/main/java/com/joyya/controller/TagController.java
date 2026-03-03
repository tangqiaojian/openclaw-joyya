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

@RestController
@RequestMapping("/api/tags")
@CrossOrigin(origins = "*")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public ResponseEntity<List<Tag>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @GetMapping("/{tagId}")
    public ResponseEntity<Tag> getTagById(@PathVariable Long tagId) {
        Optional<Tag> tag = tagService.getTagById(tagId);
        return tag.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Tag> getTagByName(@PathVariable String name) {
        Optional<Tag> tag = tagService.getTagByName(name);
        return tag.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Tag>> searchTags(@RequestParam String keyword) {
        return ResponseEntity.ok(tagService.searchTags(keyword));
    }

    @GetMapping("/identifier/{identifier}")
    public ResponseEntity<Tag> getTagByIdentifier(@PathVariable String identifier) {
        Optional<Tag> tag = tagService.findTagByIdentifier(identifier);
        return tag.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tag> saveTag(@Valid @RequestBody Tag tag) {
        try {
            Tag savedTag = tagService.saveTag(tag);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTag);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{tagId}")
    public ResponseEntity<Tag> updateTag(@PathVariable Long tagId, @Valid @RequestBody Tag tag) {
        try {
            Tag updatedTag = tagService.updateTag(tagId, tag);
            return ResponseEntity.ok(updatedTag);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<Map<String, String>> deleteTag(@PathVariable Long tagId) {
        try {
            tagService.deleteTag(tagId);
            return ResponseEntity.ok(Map.of("message", "标签删除成功"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/names")
    public ResponseEntity<List<String>> getTagNames() {
        return ResponseEntity.ok(tagService.getTagNames());
    }
}
