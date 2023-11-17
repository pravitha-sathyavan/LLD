package service;

import domain.FaceBook;
import domain.Post;
import domain.User;
import util.CustomComparator;

import java.util.*;

public class NewsFeedService {
    FaceBook faceBook;

    public NewsFeedService(FaceBook faceBook) {
        this.faceBook = faceBook;
    }

    public List<Integer> getNewsFeed(int userId, int limit){
        User user = faceBook.getUsers().get(userId);
        Set<Integer> following = user.getFollowing();
        List<Integer> posts = new ArrayList<>();
        PriorityQueue<Post> pq = new PriorityQueue<>(new CustomComparator());
        for(Integer followeeId: following){
            User followee = faceBook.getUsers().get(followeeId);
            posts.addAll(followee.getPosts());
        }
        for(Integer postId:posts){
            pq.add(faceBook.getPosts().get(postId));
        }
        List<Integer> results = new ArrayList<>();
         for(int i=0;i<limit;i++){
            if(!pq.isEmpty())
               results.add(pq.poll().getPostId());
            else
                break;
        }
        return results;
    }
    // Method Overloading
    public List<Integer> getNewsFeed(int userId) {
        return getNewsFeed(userId,faceBook.getNoOfPostsinFeed());
    }

    public List<Integer> getNewsFeedPaginated(Integer userId, Integer pageNumber) {
        User user = faceBook.getUsers().get(userId);
        if (user == null)
            return null;
        List<Integer> feed = getNewsFeed(userId, Integer.MAX_VALUE);
        Integer start = pageNumber * faceBook.getNoOfPostsinFeed();
        Integer end = Math.min(start +faceBook.getNoOfPostsinFeed(), feed.size());
        if (start > end)
            return null;
        List<Integer> paginatedFeed = feed.subList(start, end);
        System.out.println("Page number " + pageNumber + " of user " + userId + " feed");
        for (int i = 0; i < paginatedFeed.size(); i++)
            System.out.println("Post " + (i + 1) + " " + paginatedFeed.get(i));
        return paginatedFeed;
    }


}
