package model;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Review {
//    @Id -- The Id is generated automatically
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String reviewId;

    private String userId;
    private String productId;
    private Rating rating;
    private String comment;
    private List<String> media;
    private Status status;
    private Date datePosted;
    private List<String> likeduserIds;

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<String> getMedia() {
        return media;
    }

    public void setMedia(List<String> media) {
        this.media = media;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    public List<String> getLikeduserIds() {
        return likeduserIds;
    }

    public void setLikeduserIds(List<String> likeduserIds) {
        this.likeduserIds = likeduserIds;
    }

    public Review(String userId, String productId, Rating rating, String comment, Date datePosted) {
        this.userId = userId;
        this.productId = productId;
        this.rating = rating;
        this.comment = comment;
        this.datePosted = datePosted;
        this.status = Status.APPROVED;
        this.likeduserIds = new ArrayList<>();
    }

    public Review(String userId, String productId, Rating rating, String comment, List<String> media,Date datePosted) {
        this.userId = userId;
        this.productId = productId;
        this.rating = rating;
        this.media = media;
        this.comment = comment;
        this.datePosted = datePosted;
        this.status = Status.APPROVED;
        this.likeduserIds = new ArrayList<>();
    }
}
