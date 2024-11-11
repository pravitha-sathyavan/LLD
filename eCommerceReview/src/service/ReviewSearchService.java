package service;

import model.Product;
import model.Rating;
import model.Review;
import model.ReviewSystem;

import java.awt.print.Pageable;
import java.util.*;
import java.util.stream.Collectors;

public class ReviewSearchService {

    private static ReviewSearchService instance = new ReviewSearchService();
    private ReviewSearchService() {
    }
    public static synchronized ReviewSearchService getInstance() {
        if (instance == null) {
            instance = new ReviewSearchService();
        }
        return instance;
    }

    public List<Review> getReviews(String productId) {
        return ReviewSystem.getInstance().getReviews();
    }

    public double viewAverageRating(String productId) {
        Product product = ReviewSystem.getInstance().getProductById(productId);
        OptionalDouble rating = product.getReviews().stream().mapToInt(review -> review.getRating().getValue()).average();
        return rating.orElse(0.0);
    }

    public List<Review> sortReviews(String productId, SortingStrategy sortCriteria) {
        List<Review> reviews = getReviews(productId);
        return sortCriteria.sort(reviews);
    }

    // Filter reviews by keyword (comment or other fields)
    public List<Review> filterReviews(String productId, String keyword) {
        List<Review> reviews = getReviews(productId);
        return reviews.stream()
                .filter(review -> review.getComment().contains(keyword))
                .collect(Collectors.toList());
    }

    // Filter reviews by rating
    public List<Review> filterReviews(String productId, Rating rating) {
        List<Review> reviews = getReviews(productId);
        return reviews.stream()
                .filter(review -> review.getRating() == rating)
                .collect(Collectors.toList());
    }

    // Filter reviews by date
    public List<Review> filterReviews(String productId, Date date) {
        List<Review> reviews = getReviews(productId);
        return reviews.stream()
                .filter(review -> review.getDatePosted().equals(date))
                .collect(Collectors.toList());
    }
}
