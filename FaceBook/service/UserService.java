package service;

import domain.FaceBook;
import domain.User;

import java.util.List;
import java.util.Set;

public class UserService {

    FaceBook faceBook;

    public UserService(FaceBook faceBook) {
        this.faceBook = faceBook;
    }

    public void follow(int followerId, int followeeId){
        User followee = faceBook.getUsers().get(followeeId);
        followee.addFollower(followerId);
        User follower = faceBook.getUsers().get(followerId);
        follower.addFollowee(followeeId);
    }

    public void unfollow(int followerId, int followeeId){
        User followee = faceBook.getUsers().get(followeeId);
        followee.removeFollower(followerId);
        User follower = faceBook.getUsers().get(followerId);
        follower.removeFollowee(followeeId);
    }
}
