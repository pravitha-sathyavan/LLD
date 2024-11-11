package service;

import exception.ProductNotExistsException;
import model.*;
import service.sort.RateSorting;
import service.sort.SortingFactory;

import java.util.Date;
import java.util.List;

public class ReviewSystemService {
    ReviewSystem reviewSystem = ReviewSystem.getInstance();
    ReviewCreationService reviewCreationService = ReviewCreationService.getInstance();
    ReviewSearchService reviewSearchService = ReviewSearchService.getInstance();

    public void addProducts(List<Product> productList) {
        reviewSystem.setProducts(productList);
    }

    public void addReview(String userid, String productId, Rating rating, String comment, List<String> media) throws ProductNotExistsException {
        reviewCreationService.addReview(userid,productId,rating,comment,media,new Date());
    }

    public void toggleVote(String reviewId, String userId) {
        reviewCreationService.toggleVote(reviewId,userId);
    }

    public List<Review> sortandgetReviews(String productId, SORTTYPE sortType) {
        SortingFactory sortingFactory= SortingFactory.getInstance();
        SortingStrategy sortingStrategy = sortingFactory.getSortingStrategy(sortType);
        return reviewSearchService.sortReviews(productId,sortingStrategy);
    }
}
