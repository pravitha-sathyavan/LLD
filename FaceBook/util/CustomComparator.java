package util;

import domain.Post;

import java.util.Comparator;

public class CustomComparator implements Comparator<Post> {

    @Override
    public int compare(Post p1, Post p2) {
        if(p1.getCreatedAt().isAfter(p2.getCreatedAt()))
            return -1;
        else
            return 1;
    }
}
