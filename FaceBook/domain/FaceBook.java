package domain;

import java.util.HashMap;

public class FaceBook {
    HashMap<Integer,Post> posts;
    HashMap<Integer,User> users;
    Integer noOfPostsinFeed;


    public HashMap<Integer,Post> getPosts() {
        return posts;
    }

    public void setPosts(HashMap<Integer,Post> posts) {
        this.posts = posts;
    }

    public HashMap<Integer,User> getUsers() {
        return users;
    }

    public FaceBook(HashMap<Integer,Post> posts, HashMap<Integer,User> users, Integer noOfPostsinFeed) {
        this.posts = posts;
        this.users = users;
        this.noOfPostsinFeed = noOfPostsinFeed;
    }

    public void setUsers(HashMap<Integer,User> users) {
        this.users = users;
    }

    public Integer getNoOfPostsinFeed() {
        return noOfPostsinFeed;
    }

    public void setNoOfPostsinFeed(Integer noOfPostsinFeed) {
        this.noOfPostsinFeed = noOfPostsinFeed;
    }

    public void printData(){
        HashMap<Integer,Post> posts = this.posts;
        HashMap<Integer,User> users = this.users;
//        for(Post p:posts)
//            p.printPost();
//        for(User u:users)
//            u.printUser();
    }
}
