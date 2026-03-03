package com.joyya.dto;

/**
 * 标签数据传输对象（DTO）
 * 
 * @author Joyya
 * @version 1.0.0
 * @since 2026-03-03
 */
public class TagDTO {

    private Long id;
    private String name;
    private String description;
    private Integer sortOrder;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
}
