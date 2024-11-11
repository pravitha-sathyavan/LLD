package service;

import model.Review;

import java.util.List;

public interface SortingStrategy {
    List<Review> sort(List<Review> reviews);
}
