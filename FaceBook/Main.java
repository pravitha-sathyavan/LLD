import domain.FaceBook;
import domain.Post;
import domain.User;
import service.NewsFeedService;
import service.PostService;
import service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        HashMap<Integer,Post> Posts = new HashMap<>();
        HashMap<Integer,User> Users = new HashMap<>();
        Users.put(1,new User(1,"Pravitha"));
        Users.put(2,new User(2,"Amal"));
        Users.put(3,new User(3,"Sidharth"));
        FaceBook faceBook = new FaceBook(Posts,Users,3);
        NewsFeedService newsFeedService = new NewsFeedService(faceBook);
        PostService postService = new PostService(faceBook);
        UserService userService = new UserService(faceBook);
        userService.follow(1,2);
        userService.follow(2,1);
        userService.follow(3,1);
        userService.follow(2,3);
        postService.post(1,100);
        postService.post(1,101);
        postService.post(2,102);
        postService.post(2,102);
        postService.post(3,104);
        postService.post(3,105);
        List<Integer> newsFeed = newsFeedService.getNewsFeed(2);
        System.out.println(newsFeed);
        faceBook.printData();
    }
}