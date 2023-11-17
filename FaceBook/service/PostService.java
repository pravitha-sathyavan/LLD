package service;
import domain.FaceBook;
import domain.Post;
import domain.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class PostService {

    FaceBook faceBook;

    public PostService(FaceBook faceBook) {
        this.faceBook = faceBook;
    }

    public void post(int userId, int postId){
        Post newPost = new Post(postId, userId, LocalDateTime.now());
        HashMap<Integer,User> fbUsers =  faceBook.getUsers();
        HashMap<Integer,Post> fbPosts= faceBook.getPosts();
        fbPosts.put(postId,newPost);
        User user = fbUsers.get(userId);
        user.getPosts().add(postId);
    }

    public void deletePost(int postId){
        HashMap<Integer,User> fbUsers =  faceBook.getUsers();
        HashMap<Integer,Post> fbPosts= faceBook.getPosts();
        Post post = fbPosts.get(postId);
        fbPosts.remove(postId);
        User user = fbUsers.get(post.getUserId());
        user.getPosts().remove(postId);
    }
}
