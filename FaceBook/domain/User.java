package domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {
    Integer userId;
    String name;
    Set<Integer> followers;
    Set<Integer> following;

    List<Integer> posts;

    public User(Integer userId, String name) {
        this.userId = userId;
        this.name = name;
        this.posts = new ArrayList<>();
        this.followers = new HashSet<>();
        this.following = new HashSet<>();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Integer> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<Integer> followers) {
        this.followers = followers;
    }

    public Set<Integer> getFollowing() {
        return following;
    }

    public void setFollowing(Set<Integer> following) {
        this.following = following;
    }

    public List<Integer> getPosts() {
        return posts;
    }

    public void setPosts(List<Integer> posts) {
        this.posts = posts;
    }

    public void printUser(){
        System.out.println("User:");
        System.out.println(this.userId);
        System.out.println(this.followers);
        System.out.println(this.following);
        System.out.println("--------");
    }

    public void addFollower(int followerId) {
        this.followers.add(followerId);
    }

    public void addFollowee(int followeeId) {
        this.following.add(followeeId);
    }

    public void removeFollower(int followerId) {
        this.followers.remove(followerId);
    }

    public void removeFollowee(int followeeId) {
        this.following.remove(followeeId);
    }
}
