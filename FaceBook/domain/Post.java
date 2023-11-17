package domain;

import java.time.LocalDateTime;

public class Post {
    Integer postId;
    Integer userId;
    LocalDateTime createdAt;

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Post(Integer postId, Integer userId, LocalDateTime createdAt) {
        this.postId = postId;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public void printPost(){
        System.out.println("Post:");
        System.out.println(this.postId);
        System.out.println(this.userId);
        System.out.println("---------");
    }
}
