package service;
import exception.ProductNotExistsException;
import exception.ReviewNotExistsException;
import model.Product;
import model.Rating;
import model.Review;
import model.ReviewSystem;

import java.util.Date;
import java.util.List;

public class ReviewCreationService {

    private static ReviewCreationService reviewCreationService;
    private ReviewCreationService() {

    }

    public static synchronized ReviewCreationService getInstance() {
        if (reviewCreationService == null) {
            reviewCreationService = new ReviewCreationService();
        }
        return reviewCreationService;
    }

    public boolean addReview(String userId, String productId, Rating rating, String comment, List<String> media, Date datePosted) throws ProductNotExistsException {
        if(validateReviewContent(comment)) {
            Review review;
            if (null != media)
                review = new Review(userId, productId, rating, comment, media, datePosted);
            else
                review = new Review(userId, productId, rating, comment, datePosted);
            return addReviewInproduct(productId, review);
        }
        return false;
    }

    private boolean addReviewInproduct(String productId, Review review) throws ProductNotExistsException {
        Product product  = ReviewSystem.getInstance().getProductById(productId);
        if(null!=product){
            List<Review> reviews = product.getReviews();
            reviews.add(review);
            product.setReviews(reviews);
            return true;
        }
        throw new ProductNotExistsException("Product not exists");
    }

    public void toggleVote(String reviewId, String userId) {
        // Toggle the vote: If user has upvoted, then remove it; if not, add an upvote
        Review review = findReviewById(reviewId);
        if (review != null) {
            List<String> userIds = review.getLikeduserIds();
            if (userIds.contains(userId)) {
                userIds.remove(userId);
            }else {
                userIds.add(userId);
            }
            review.setLikeduserIds(userIds);
        }
    }

    public void editReview(String reviewId, Rating newRating, String newComment, boolean keepMedia) throws ReviewNotExistsException {
        // Find the review and update its fields
        Review review = findReviewById(reviewId);
        if (review != null) {
            review.setRating(newRating);
            review.setComment(newComment);
            if (!keepMedia) {
                review.setMedia(null);
            }
        }
        throw new ReviewNotExistsException("Review does not exists");
    }

    public void deleteReview(String reviewId) {
        Review review = findReviewById(reviewId);
        if (review != null) {
            removeReviewFromProduct(reviewId, review.getProductId());
        }
    }

    private void removeReviewFromProduct(String reviewId, String productId) {
        Product product = ReviewSystem.getInstance().getProductById(productId);
        List<Review> reviews = product.getReviews();
        for (Review review : reviews) {
            if (review.getReviewId().equals(reviewId)) {
                reviews.remove(review);
                product.setReviews(reviews);
                break;
            }
        }
    }

    public boolean validateReviewContent(String content) {
        // Implement validation logic, e.g., checking for offensive words
        return !content.contains("offensiveWord");
    }

    // Utility method to find a review by ID (for simplicity)
    private Review findReviewById(String reviewId) {
        return ReviewSystem.getInstance().getReviewById(reviewId);
    }

}
