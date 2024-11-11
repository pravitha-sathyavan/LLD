package model;

import java.util.ArrayList;
import java.util.List;

public class ReviewSystem {
    List<Product> products;
    List<Review> reviews;
    private  static ReviewSystem reviewSystem;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    private ReviewSystem() {
        products = new ArrayList<Product>();
    }

    public static synchronized  ReviewSystem getInstance() {
        if(reviewSystem == null)
            reviewSystem = new ReviewSystem();
        return reviewSystem;
    }

    public Product getProductById(String id) {
        for(Product p : products) {
            if(p.getProductId().equals(id))
                return p;
        }
        return null;
    }

    public Review getReviewById(String id) {
        for(Review r : reviews) {
            if (r.getReviewId().equals(id));
                return r;
        }
        return null;
    }


}
