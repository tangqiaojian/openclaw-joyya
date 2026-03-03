package com.joyya.dto;

import java.time.LocalDateTime;

/**
 * 用户数据传输对象（DTO）
 * 
 * @author Joyya
 * @version 1.0.0
 * @since 2026-03-03
 */
public class UserDTO {

    private Long id;
    private String username;
    private String email;
    private String avatar;
    private String nickname;
    private String signature;
    private Integer gender;
    private Integer level;
    private Long coins;
    private Long followers;
    private Long following;
    private Boolean isVerified;
    private LocalDateTime createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public String getSignature() { return signature; }
    public void setSignature(String signature) { this.signature = signature; }
    public Integer getGender() { return gender; }
    public void setGender(Integer gender) { this.gender = gender; }
    public Integer getLevel() { return level; }
    public void setLevel(Integer level) { this.level = level; }
    public Long getCoins() { return coins; }
    public void setCoins(Long coins) { this.coins = coins; }
    public Long getFollowers() { return followers; }
    public void setFollowers(Long followers) { this.followers = followers; }
    public Long getFollowing() { return following; }
    public void setFollowing(Long following) { this.following = following; }
    public Boolean getIsVerified() { return isVerified; }
    public void setIsVerified(Boolean isVerified) { this.isVerified = isVerified; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
